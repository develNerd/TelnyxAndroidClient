package org.telnyx.androidclient.usecases

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in Input,out T>(){
    abstract suspend fun dispatch(input: Input):T
}

abstract class FlowBaseUseCase<in Input,out T>(){
    abstract suspend fun dispatch(input: Input):Flow<T>
}


