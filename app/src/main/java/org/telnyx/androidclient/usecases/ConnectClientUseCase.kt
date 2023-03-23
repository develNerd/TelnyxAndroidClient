package org.telnyx.androidclient.usecases

import com.telnyx.webrtc.sdk.TelnyxClient

class ConnectClientUseCase(private val client: TelnyxClient) : BaseUseCase<Unit,Unit>() {
    override suspend fun dispatch(input: Unit) {
        client.connect()
    }
}