package org.telnyx.androidclient.ui.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.telnyx.webrtc.sdk.verto.receive.InviteResponse
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.telnyx.androidclient.R
import org.telnyx.androidclient.ui.core.BaseComposeFragment
import org.telnyx.androidclient.ui.theme.Colors
import org.telnyx.androidclient.ui.theme.Dimens
import org.telnyx.androidclient.ui.viewcomponets.CallEdittext
import org.telnyx.androidclient.ui.viewcomponets.ColumnInset
import org.telnyx.androidclient.ui.viewcomponets.MediumTextBold
import org.telnyx.androidclient.ui.viewcomponets.RegularText
import org.telnyx.androidclient.util.getFormattedStopWatch
import kotlin.Boolean
import kotlin.Int
import kotlin.OptIn
import kotlin.Unit
import kotlin.getValue

class HomeFragment : BaseComposeFragment() {

    private val viewModel by activityViewModel<MainViewModel>()
    private var currentInvite: InviteResponse? = null

    enum class CallState {
        INCOMING, OUTGOING, ONGOING, IDLE
    }

    private var countDownTimer: Handler? = null
    private var runnable: Runnable? = null
    private val TIMER_DELAY = 1000

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    override fun InitComposeUI() {

        val uiState =
            viewModel.uiStatus.collectAsState(MainViewModel.UiState.ConnectionResult()).value
        var connectionStatus by remember {
            mutableStateOf("")
        }
        var clientAuthenticated by remember {
            mutableStateOf(false)
        }

        var callStatus by remember {
            mutableStateOf("")
        }

        var callState by remember {
            mutableStateOf(CallState.IDLE)
        }


        callStatus = when (callState) {
            CallState.IDLE -> {
                runnable?.let { countDownTimer?.removeCallbacks(it) }
                viewModel.setCallDuration()
                stringResource(id = R.string.inactive)
            }
            CallState.OUTGOING -> stringResource(id = R.string.calling)
            CallState.ONGOING -> {
                stringResource(id = R.string.call_accepted)
            }
            CallState.INCOMING -> stringResource(id = R.string.incoming_call)
        }

        Log.e("CallState", callState.toString())

        when (uiState) {

            is MainViewModel.UiState.ConnectionResult -> {
                /*Check is Client is Ready to make or receive a call*/
                when {
                    //Initial Connection
                    uiState.status == MainViewModel.ConnectionStatus.LOADING  -> {
                        connectionStatus = stringResource(id = R.string.not_connected)
                    }
                    //Connection Failed
                    uiState.status == MainViewModel.ConnectionStatus.ERROR  -> {
                        connectionStatus = uiState.error ?: ""
                        viewModel.connectUser(loginOnDemand = true, delay = 3000L)
                    }
                    //Connected but Not Logged In
                    uiState.status == MainViewModel.ConnectionStatus.CONNECTED -> {
                        viewModel.loginUser()
                    }
                    uiState.status == MainViewModel.ConnectionStatus.CONNECTED_AND_LOGGED_IN  -> {
                        connectionStatus = stringResource(id = R.string.connected_authenticated)
                    }
                    else -> {
                        connectionStatus = stringResource(id = R.string.connected_pending_auth)
                    }
                }
                clientAuthenticated =  uiState.status == MainViewModel.ConnectionStatus.CONNECTED_AND_LOGGED_IN
            }
            is MainViewModel.UiState.CallAccepted -> {
                callState = CallState.ONGOING
            }
            is MainViewModel.UiState.CallCancelled -> {

                callState = CallState.IDLE
            }
            is MainViewModel.UiState.IncomingCall -> {
                currentInvite = uiState.invite
                callState = CallState.INCOMING
            }
            MainViewModel.UiState.Calling -> {
                callState = CallState.OUTGOING
            }
        }


        val (destNumber, setDestNumber) = remember {
            mutableStateOf("")
        }


        ColumnInset(
            bottomBar = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.callButtonsPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            Dimens.mediumPadding
                        )
                    ) {

                        CallButtonItem(
                            icon = R.drawable.baseline_call_24,
                            backGround = Colors.colorPrimary,
                            enabled = destNumber.isNotEmpty() || callState == CallState.INCOMING
                        ) {
                            if (callState == CallState.INCOMING) {
                                viewModel.acceptInvite(currentInvite)
                                countDownTimer!!.post(runnable!!);
                            } else {
                                viewModel.makeCall(destNumber)
                            }
                        }

                        CallButtonItem(
                            icon = R.drawable.baseline_call_end_24,
                            backGround = Colors.colorError,
                            enabled = callState == CallState.INCOMING || callState == CallState.ONGOING || callState == CallState.OUTGOING
                        ) {
                            viewModel.endCall()
                        }


                    }
                }
            }) {

            MainHeader(true) {
                findNavController().navigate(R.id.action_homeFragment_to_loginScreen)
                viewModel.resetCredential()
            }
            MediumTextBold(
                text = connectionStatus,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding),
                textAlign = TextAlign.Center,
                color = if (clientAuthenticated) Colors.colorPrimary else MaterialTheme.colorScheme.onBackground
            )
            CallEdittext(
                hint = stringResource(id = R.string.input_number_here),
                text = destNumber,
                stroke = BorderStroke(1.dp, Colors.colorPrimary),
                onTextChanged = setDestNumber
            )
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.largePadding)) {
                MediumTextBold(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.largePadding),
                    text = callStatus,
                    color = if (callState == CallState.ONGOING) Colors.colorWarning else MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                val callDuration by viewModel.callDuration.collectAsState(0L)
                if (callDuration != 0L){
                    RegularText(
                        text = getFormattedStopWatch(callDuration),
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

            }


        }
    }

    override fun viewCreated() {
        countDownTimer = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                countDownTimer!!.postDelayed(this, TIMER_DELAY.toLong())
                viewModel.setCallDuration(viewModel.callDuration.value + 1)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /*User is Already Logged In*/
        if (prefManager.userCredential == null) {
            findNavController().navigate(R.id.action_homeFragment_to_loginScreen)
            return
        }
        viewModel.connectUser()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CallButtonItem(icon: Int, backGround: Color, enabled: Boolean, onClick: () -> Unit) {
        Card(
            colors = CardDefaults.cardColors(backGround),
            modifier = Modifier
                .size(Dimens.callIconSize), shape = RoundedCornerShape(100),
            onClick = onClick, enabled = enabled
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        id = icon
                    ),
                    modifier = Modifier.padding(Dimens.mediumPadding),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }


}