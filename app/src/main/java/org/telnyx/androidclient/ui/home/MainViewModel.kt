package org.telnyx.androidclient.ui.home

import androidx.lifecycle.viewModelScope
import com.telnyx.webrtc.sdk.CredentialConfig
import com.telnyx.webrtc.sdk.model.SocketMethod
import com.telnyx.webrtc.sdk.model.SocketStatus
import com.telnyx.webrtc.sdk.verto.receive.AnswerResponse
import com.telnyx.webrtc.sdk.verto.receive.InviteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.telnyx.androidclient.ui.core.BaseViewModel
import org.telnyx.androidclient.usecases.CallClientUseCaseFactory
import java.util.UUID
import kotlin.random.Random

class MainViewModel(private val callClientUseCaseFactory: CallClientUseCaseFactory) :
    BaseViewModel() {

    private val _uiStatus = MutableStateFlow<UiState>(UiState.ConnectionResult())
    val uiStatus: StateFlow<UiState> = _uiStatus

    private val _callDuration = MutableStateFlow(0L)
    val callDuration: StateFlow<Long>
        get() = _callDuration

    fun setCallDuration(value:Long = 0L){
        _callDuration.value = value
    }

    private var credentialConfig: CredentialConfig? = null

    private var currentCallUUID: UUID? = null


    fun loginUser(userName: String? = null, password: String? = null) {
        credentialConfig = if (userName != null && password != null) {
            CredentialConfig(
                userName.trim(),
                password.trim(),
                userName + Random(4).nextInt(),
                UUID.randomUUID().toString(),
                null,
                null,
                null
            )
        } else null
        executeUseCase(
            inputValue = credentialConfig,
            useCase = callClientUseCaseFactory.loginUseCase,
            error = {

            }
        )

    }

    fun connectUser(loginOnDemand: Boolean = false, userName: String = "", password: String = "",delay:Long = 0L) {
        viewModelScope.launch {
            delay(delay)
            executeUseCase(Unit, callClientUseCaseFactory.connectClientUseCase, error = {}, callback = {
                if (loginOnDemand) {
                    loginUser(userName, password)
                }
            })
        }
    }

    fun streamClientResponse() {
        executeFlowUseCase(viewModelScope,Unit, callClientUseCaseFactory.socketResponseUseCase, error = {

        }) {
            viewModelScope.launch(Dispatchers.Default) {
                //Migrating To Sealed Classes Would Make this Cleaner
                when (it.status) {
                    SocketStatus.ESTABLISHED -> {
                        _uiStatus.value = (UiState.ConnectionResult(
                            ConnectionStatus.CONNECTED,
                            null
                        ))
                    }
                    SocketStatus.ERROR -> {
                        _uiStatus.value = (UiState.ConnectionResult(
                            ConnectionStatus.ERROR,
                            it.errorMessage
                        ))
                    }
                    SocketStatus.LOADING -> {
                        _uiStatus.value = (UiState.ConnectionResult())

                    }
                    SocketStatus.MESSAGERECEIVED -> {
                        when (it.data?.method) {
                            SocketMethod.LOGIN.methodName -> {}
                            SocketMethod.INVITE.methodName -> {
                                //acceptInvite(it.data?.result as InviteResponse)
                                currentCallUUID = (it.data?.result as InviteResponse).callId
                                _uiStatus.value =
                                    (UiState.IncomingCall(invite = it.data?.result as InviteResponse))
                            }

                            SocketMethod.ANSWER.methodName -> {
                                currentCallUUID = (it.data?.result as AnswerResponse).callId
                                _uiStatus.value = (UiState.CallAccepted)
                            }

                            SocketMethod.BYE.methodName -> {
                                _uiStatus.value = (UiState.CallCancelled)
                            }
                            SocketMethod.CLIENT_READY.methodName -> {
                                _uiStatus.value = (UiState.ConnectionResult(
                                    ConnectionStatus.CONNECTED_AND_LOGGED_IN,
                                    null
                                ))
                            }
                        }
                    }
                    SocketStatus.DISCONNECT -> {
                        _uiStatus.emit(
                            UiState.ConnectionResult(
                                ConnectionStatus.LOADING,
                                null
                            )
                        )
                    }
                }
            }
        }
    }

    fun saveCredential() {
        if (credentialConfig != null) {
            executeUseCase(
                inputValue = credentialConfig!!,
                callClientUseCaseFactory.saveCredentialUseCase
            )
        }
    }

    fun initClientResult(){
        _uiStatus.value = UiState.ConnectionResult()
    }
    fun resetCredential() {
        executeUseCase(
            inputValue = null,
            callClientUseCaseFactory.saveCredentialUseCase
        )
    }

    fun acceptInvite(invite: InviteResponse?) {
        invite ?: return
        executeUseCase(inputValue = invite, callClientUseCaseFactory.acceptCallUseCase, error = {

        })
        _uiStatus.value = (UiState.CallAccepted)
    }

    fun makeCall(number: String) {
        executeUseCase(
            inputValue = number.trim(),
            callClientUseCaseFactory.makeCallUseCase,
            error = {

            })
        _uiStatus.value = (UiState.Calling)
    }

    fun endCall() {
        currentCallUUID ?: return
        executeUseCase(
            inputValue = currentCallUUID!!,
            callClientUseCaseFactory.endCallUseCase,
            error = {})
        _uiStatus.value = (UiState.CallCancelled)
    }

    enum class ConnectionStatus{
        LOADING,
        ERROR,
        CONNECTED,
        CONNECTED_AND_LOGGED_IN
    }

    sealed class UiState {
        data class ConnectionResult(
            var status:ConnectionStatus = ConnectionStatus.LOADING,
            var error: String? = null,
        ) : UiState()

        data class IncomingCall(
            val invite: InviteResponse?
        ) : UiState()

        object CallCancelled : UiState()
        object CallAccepted : UiState()
        object Calling : UiState()

    }
}