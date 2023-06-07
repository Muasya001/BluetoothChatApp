package com.example.bluetoothchat.presentation

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bluetoothchat.ui.theme.BluetoothChatTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.M)
@AndroidEntryPoint
class MainActivity : ComponentActivity(

) {
        private val bluetoothManager by lazy {
            applicationContext.getSystemService(BluetoothManager::class.java)
        }
        private val bluetoothAdapter by lazy {
            bluetoothManager?.adapter
        }
    private val isBlueToothEnabled :Boolean
        get()= bluetoothAdapter?.isEnabled==true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val enableBlueToothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            /* maybe not needed*/

        }
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        {
                perms -> val canEnableBluetooth = if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            perms[Manifest.permission.BLUETOOTH_CONNECT]==true
        } else true
            if (canEnableBluetooth&&! isBlueToothEnabled){
                enableBlueToothLauncher.launch(
                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                )
            }
        }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT
                    )
                )
            }

        setContent {
            BluetoothChatTheme {
                val viewModel = hiltViewModel<BlueToothViewModel>()
                val state by viewModel.state.collectAsState()
                Surface(color = MaterialTheme.colorScheme.background) {
                    DeviceScreen(
                        state= state,
                        onStartScan = viewModel::startScan,
                        onStopScan = viewModel::stopScan

                    )
                    
                }


            }
        }
    }
}
