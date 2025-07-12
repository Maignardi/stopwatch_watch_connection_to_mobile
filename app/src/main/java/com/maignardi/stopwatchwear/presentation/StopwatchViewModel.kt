package com.maignardi.stopwatchwear.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class StopwatchViewModel : ViewModel() {
    private val _time = MutableStateFlow(0L)
    val time: StateFlow<Long> = _time

    private val _running = MutableStateFlow(false)
    val running: StateFlow<Boolean> = _running

    private var job: Job? = null

    fun start() {
        if (_running.value) return
        _running.value = true
        val startTime = System.currentTimeMillis() - _time.value
        job = viewModelScope.launch {
            while (isActive && _running.value) {
                _time.value = System.currentTimeMillis() - startTime
                delay(100)
            }
        }
    }

    fun stop() {
        _running.value = false
        job?.cancel()
        job = null
    }

    fun reset() {
        stop()
        _time.value = 0L
    }
}
