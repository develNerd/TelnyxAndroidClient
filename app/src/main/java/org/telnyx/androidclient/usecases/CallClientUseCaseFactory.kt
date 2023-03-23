package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.TelnyxClient
import org.telnyx.androidclient.repositories.CredentialsProvider

class CallClientUseCaseFactory(credentialsProvider: CredentialsProvider,client:TelnyxClient) {
    val socketResponseUseCase = GetSocketResponseUseCase(client)
    val loginUseCase = LoginUseCase(client,credentialsProvider)
    val connectClientUseCase = ConnectClientUseCase(client)
    val acceptCallUseCase = AcceptCallUseCase(client)
    val makeCallUseCase = MakeCallUseCase(client,credentialsProvider)
    val endCallUseCase = EndCallUseCase(client)
    val saveCredentialUseCase = SaveCredentialUseCase(credentialsProvider)
}