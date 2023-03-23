package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.CredentialConfig
import com.telnyx.webrtc.sdk.TelnyxClient
import org.telnyx.androidclient.repositories.CredentialsProvider

class LoginUseCase(private val client: TelnyxClient,private val credentialsProvider: CredentialsProvider) : BaseUseCase<CredentialConfig?,Unit>() {
    override suspend fun dispatch(input: CredentialConfig?) {
        client.credentialLogin(input ?: credentialsProvider.getCredentials()!!)
    }
}