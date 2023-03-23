package org.telnyx.androidclient.ui.core.viewmodelstub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.telnyx.androidclient.usecases.FlowBaseUseCase

class FlowTestUseCase : FlowBaseUseCase<Any, Int>() {
    override suspend fun dispatch(input: Any): Flow<Int> {
        return flow { emit(input.hashCode()) }
    }
}