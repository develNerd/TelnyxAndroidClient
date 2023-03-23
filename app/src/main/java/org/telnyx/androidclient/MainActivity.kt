package org.telnyx.androidclient

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.telnyx.androidclient.data.PrefManager
import org.telnyx.androidclient.databinding.ActivityMainBinding
import org.telnyx.androidclient.ui.home.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        viewModel.streamClientResponse()


        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
               //Granted Access
            }
        checkPermission(requestPermissionLauncher)

    }

    private fun checkPermission(requestPermissionLauncher:ActivityResultLauncher<String>) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {}
            shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO) -> {
                //Write Logic
            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.RECORD_AUDIO
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

