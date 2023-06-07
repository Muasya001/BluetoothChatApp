package com.example.bluetoothchat.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bluetoothchat.data.chat.AndroidBluetoothController
import com.example.bluetoothchat.domain.chat.BluetoothController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController{
        return AndroidBluetoothController(context)

    }
}