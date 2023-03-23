package org.telnyx.androidclient.usecases

import androidx.lifecycle.asFlow
import com.telnyx.webrtc.sdk.TelnyxClient
import com.telnyx.webrtc.sdk.verto.receive.ReceivedMessageBody
import com.telnyx.webrtc.sdk.verto.receive.SocketResponse
import kotlinx.coroutines.flow.Flow

class GetSocketResponseUseCase(private val client:TelnyxClient) : FlowBaseUseCase<Unit,SocketResponse<ReceivedMessageBody?>>() {
    override suspend fun dispatch(input: Unit): Flow<SocketResponse<ReceivedMessageBody?>> {
     return  client.getSocketResponse().asFlow()
    }
}