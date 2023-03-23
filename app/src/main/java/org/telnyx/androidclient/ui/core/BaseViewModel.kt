package org.telnyx.androidclient.ui.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.telnyx.androidclient.usecases.BaseUseCase
import org.telnyx.androidclient.usecases.FlowBaseUseCase


abstract class BaseViewModel : ViewModel() {


    fun <Input,Output:Any> executeUseCase(
        inputValue:Input,
        useCase: BaseUseCase<Input, Output>,
        error: (Exception) -> Unit = {},
        callback: (Output) -> Unit = {},
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
               callback(useCase.dispatch(inputValue))
            }catch (E:Exception){
                E.printStackTrace()
                Log.e("ERROR OCCURRED", E.localizedMessage ?: "")
                error(E)
            }
        }
    }


    fun <Input,Output:Any> executeFlowUseCase(
        scope: CoroutineScope,
        inputValue:Input,
        useCase: FlowBaseUseCase<Input, Output>,
        error: (Exception) -> Unit = {},
        callback: (Output) -> Unit = {},
    ){
        scope.launch(Dispatchers.IO) {
            try {
                useCase.dispatch(inputValue).collectLatest {result ->
                    Log.i("CLIENT_MESSAGE_RECEIVED",result.toString())
                    callback(result)
                }
            }catch (E:Exception){
                E.printStackTrace()
                Log.e("ERROR OCCURRED", E.localizedMessage ?: "")
                error(E)
            }
        }
    }

}