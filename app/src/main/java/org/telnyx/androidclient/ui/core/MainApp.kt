package org.telnyx.androidclient.ui.core

import android.app.Application
import com.telnyx.webrtc.sdk.TelnyxClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.telnyx.androidclient.data.PrefManager
import org.telnyx.androidclient.repositories.CredentialProviderImpl
import org.telnyx.androidclient.repositories.CredentialsProvider
import org.telnyx.androidclient.ui.home.MainViewModel
import org.telnyx.androidclient.usecases.CallClientUseCaseFactory

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialise Koin for Dependency Injection
        startKoin {
            modules(presentationModule)
            androidContext(this@MainApp)
        }

    }


}

val presentationModule = module {
    single { TelnyxClient(androidContext()) }
    single { PrefManager(androidContext()) }
    singleOf(::CredentialProviderImpl){ bind<CredentialsProvider>() }
    single { CallClientUseCaseFactory(get(),get()) }
    viewModelOf(::MainViewModel)
}
