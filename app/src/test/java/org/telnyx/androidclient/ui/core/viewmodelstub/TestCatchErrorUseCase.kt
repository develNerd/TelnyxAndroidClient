package org.telnyx.androidclient.ui.core.viewmodelstub

import org.telnyx.androidclient.usecases.BaseUseCase

class TestCatchErrorUseCase : BaseUseCase<Any, Int>() {
    override suspend fun dispatch(input: Any): Int {
        return input.hashCode() / 0
    }
}