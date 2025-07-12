package com.maignardi.stopwatchwear.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*

@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel,
    onStartClick: () -> Unit
) {
    val time by viewModel.time.collectAsState()
    val running by viewModel.running.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatTime(time),
            style = MaterialTheme.typography.title2
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onStartClick) {
            Text(if (running) "Stop" else "Start")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.reset() },
            enabled = !running
        ) {
            Text("Reset")
        }
    }
}

fun formatTime(ms: Long): String {
    val total = ms / 1000
    val min = total / 60
    val sec = total % 60
    return "%02d:%02d".format(min, sec)
}
