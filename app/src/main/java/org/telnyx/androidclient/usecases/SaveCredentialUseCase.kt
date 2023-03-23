package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.CredentialConfig
import com.telnyx.webrtc.sdk.TelnyxClient
import org.telnyx.androidclient.repositories.CredentialsProvider

class SaveCredentialUseCase(private val credentialsProvider: CredentialsProvider) : BaseUseCase<CredentialConfig?,Unit>() {
    override suspend fun dispatch(input: CredentialConfig?) {
        credentialsProvider.saveCredentials(input)
    }
}