package org.telnyx.androidclient.repositories

import com.telnyx.webrtc.sdk.CredentialConfig
import org.telnyx.androidclient.data.UserCredential

interface CredentialsProvider {
    fun getCredentials() : CredentialConfig?

    fun saveCredentials(credentialConfig: CredentialConfig?)
}