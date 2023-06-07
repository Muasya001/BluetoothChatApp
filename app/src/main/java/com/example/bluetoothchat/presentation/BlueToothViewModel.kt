package com.example.bluetoothchat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothchat.domain.chat.BluetoothController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BlueToothViewModel @Inject constructor(
    private val blueToothController : BluetoothController
): ViewModel() {
    private val _state = MutableStateFlow(BluetoothUiState())
    val state = combine(
        blueToothController.pairedDevices,
        blueToothController.pairedDevices,
        _state
    ){
        scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices= scannedDevices,
            pairedDevices = pairedDevices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(7000), _state.value)
     fun startScan(){
         blueToothController.startDiscovery()
     }
    fun stopScan(){
        blueToothController.stopDiscovery()
    }
}