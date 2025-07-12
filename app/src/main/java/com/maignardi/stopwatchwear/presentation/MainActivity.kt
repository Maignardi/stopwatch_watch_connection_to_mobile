package com.maignardi.stopwatchwear.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private lateinit var communicator: PhoneCommunicator
    private lateinit var viewModel: StopwatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[StopwatchViewModel::class.java]

        communicator = PhoneCommunicator(this) { msg ->
            Log.d("MainActivity", "Message received from phone: $msg")
            when (msg) {
                "start" -> viewModel.start()
                "stop" -> viewModel.stop()
                "reset" -> viewModel.reset()
                else -> Log.w("MainActivity", "Unknown message: $msg")
            }
        }

        setContent {
            StopwatchScreen(
                viewModel = viewModel,
                onStartClick = {
                    if (viewModel.running.value) {
                        viewModel.stop()
                        Log.d("MainActivity", "Sending: stop")
                        communicator.sendToPhone("stop")
                    } else {
                        viewModel.start()
                        Log.d("MainActivity", "Sending: start")
                        communicator.sendToPhone("start")
                    }
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        communicator.unregister()
        Log.d("MainActivity", "Unregistered communicator")
    }
}
