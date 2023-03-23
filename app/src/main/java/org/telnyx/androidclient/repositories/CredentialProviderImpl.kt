package org.telnyx.androidclient.repositories

import com.telnyx.webrtc.sdk.CredentialConfig
import org.telnyx.androidclient.R
import org.telnyx.androidclient.data.PrefManager
import org.telnyx.androidclient.data.UserCredential

class CredentialProviderImpl(private val prefManager: PrefManager) : CredentialsProvider {
    override fun getCredentials(): CredentialConfig? {
        val credential =  prefManager.userCredential
        return if (credential != null)
            CredentialConfig(credential.userName,credential.password,credential.callerIDName,credential.callerIdNumber,null,R.raw.ringtone,R.raw.ringtone)
        else null
    }

    override fun saveCredentials(credentialConfig: CredentialConfig?) {
        if (credentialConfig != null){
            prefManager.userCredential = UserCredential(credentialConfig.sipUser,credentialConfig.sipPassword,credentialConfig.sipCallerIDName ?:"",credentialConfig.sipCallerIDNumber ?: "")
        }else{
            //Logout User
            prefManager.userCredential = null
        }
    }

}