package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.TelnyxClient
import com.telnyx.webrtc.sdk.verto.receive.InviteResponse

class AcceptCallUseCase(private val client: TelnyxClient) : BaseUseCase<InviteResponse,Unit>() {
    override suspend fun dispatch(input: InviteResponse) {
        client.call?.acceptCall(input.callId,input.callerIdNumber)
    }
}