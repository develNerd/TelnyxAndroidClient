package org.telnyx.androidclient.ui.core.viewmodelstub

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.telnyx.androidclient.ui.core.BaseViewModel

class FakeViewModel(private val testUseCaseFactory: TestUseCaseFactory) : BaseViewModel() {

    /** @see Any.hashCode Callback expects a hashcode of input   */
    fun execute_use_case_test(input:Any,error:(Exception) -> Unit = {},callBack:(Any) -> Unit){
        executeUseCase(input,testUseCaseFactory.testUseCase, error = error, callback = callBack)
    }
    /** @see Any.hashCode Callback expects a  hashcode flow of input   */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun execute_flow_use_case_test(input:Any, error:(Exception) -> Unit = {}, callBack:(Any) -> Unit){

        executeFlowUseCase(TestScope(),input,testUseCaseFactory.flowTestUseCase, error = error, callback = callBack)
    }
    /** @see Any.hashCode Callback expects a  hashcode flow of input   */
    fun execute_error_use_case_test(input:Any,error:(Exception) -> Unit = {},callBack:(Any) -> Unit = {}){
        executeUseCase(input,testUseCaseFactory.errorUseCase, error = error, callback = callBack)
    }

}