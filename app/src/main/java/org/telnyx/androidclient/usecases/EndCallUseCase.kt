package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.TelnyxClient
import java.util.UUID

class EndCallUseCase(private val client: TelnyxClient) : BaseUseCase<UUID,Unit>() {
    override suspend fun dispatch(input: UUID) {
        client.call?.endCall(input)

    }
}