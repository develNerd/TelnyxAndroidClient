package org.telnyx.androidclient.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class UserCredential(
    @SerialName("userName") val userName: String,
    @SerialName("password") val password: String,
    @SerialName("callerIDName") val callerIDName: String,
    @SerialName("callerIdNumber") val callerIdNumber: String
)
