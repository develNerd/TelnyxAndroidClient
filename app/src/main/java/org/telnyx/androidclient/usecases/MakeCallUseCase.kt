package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.TelnyxClient
import com.telnyx.webrtc.sdk.verto.receive.InviteResponse
import org.telnyx.androidclient.repositories.CredentialsProvider

class MakeCallUseCase(private val client: TelnyxClient,private val credentialsProvider: CredentialsProvider) : BaseUseCase<String,Unit>() {
    override suspend fun dispatch(input: String) {
        credentialsProvider.getCredentials()?.apply {
            client.call?.newInvite(callerName = sipCallerIDName ?: "", callerNumber = sipCallerIDNumber ?: "",input,"login")
        }
    }
}