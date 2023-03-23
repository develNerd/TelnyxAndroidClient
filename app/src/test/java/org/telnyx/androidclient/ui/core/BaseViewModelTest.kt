package org.telnyx.androidclient.ui.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.telnyx.androidclient.ui.core.viewmodelstub.FakeViewModel
import org.telnyx.androidclient.ui.core.viewmodelstub.TestUseCaseFactory
import kotlin.random.Random

class BaseViewModelTest {

    private val fakeViewModel = FakeViewModel(TestUseCaseFactory())

    @Test
    fun executeUseCase() {
        runBlocking {
            val randomInput = Random(1000).nextDouble()
            var result:Any? = null
            fakeViewModel.execute_use_case_test(randomInput){
                result = it
            }
            delay(200)
            assertEquals(randomInput.hashCode(),result)
        }
    }

    @Test
    fun executeFlowUseCase() {
        runBlocking {
            val randomInput = Random(1000).nextDouble()
            var result:Any? = null
            fakeViewModel.execute_flow_use_case_test(randomInput){
                result = it
            }
            delay(200)
            assertEquals(randomInput.hashCode(),result)
        }
    }

    @Test
    fun executeCatchErrorUseCase() {
        runBlocking {
            val randomInput = Random(1000).nextDouble()
            var result:Any? = null
            fakeViewModel.execute_error_use_case_test(randomInput, error = {
                result = it
            })
            delay(200)
            assert(result is Exception)
        }

    }
}