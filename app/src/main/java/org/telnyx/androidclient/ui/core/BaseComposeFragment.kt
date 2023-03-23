package org.telnyx.androidclient.ui.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import org.koin.android.ext.android.inject
import org.telnyx.androidclient.data.PrefManager
import org.telnyx.androidclient.ui.theme.TelnyxAndroidClientTheme


abstract class BaseComposeFragment : Fragment() {

    val prefManager: PrefManager by inject()

    @Composable
    abstract fun InitComposeUI()

    abstract fun viewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireActivity()).apply {
            this.fitsSystemWindows = true
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TelnyxAndroidClientTheme {
                    Box {
                        InitComposeUI()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
    }



}