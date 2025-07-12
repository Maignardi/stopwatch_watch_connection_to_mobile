package com.maignardi.stopwatchwear.presentation

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class PhoneCommunicator(
    context: Context,
    private val onMessage: (String) -> Unit
) : MessageClient.OnMessageReceivedListener {

    private val client = Wearable.getMessageClient(context)
    private val nodeClient = Wearable.getNodeClient(context)

    init {
        Log.d("PhoneCommunicator", "Registering MessageClient listener...")
        client.addListener(this)
    }

    fun unregister() {
        Log.d("PhoneCommunicator", "Unregistering MessageClient listener")
        client.removeListener(this)
    }

    fun sendToPhone(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val nodes = nodeClient.connectedNodes.await()
                Log.d("PhoneCommunicator", "Connected nodes: ${nodes.size}")
                if (nodes.isEmpty()) {
                    Log.w("PhoneCommunicator", "No nodes connected. Message not sent.")
                }
                nodes.forEach { node ->
                    Log.d("PhoneCommunicator", "Sending message: \"$message\" to ${node.displayName}")
                    client.sendMessage(node.id, "/sync", message.toByteArray()).await()
                }
            } catch (e: Exception) {
                Log.e("PhoneCommunicator", "Error sending message to phone: ${e.message}", e)
            }
        }
    }

    override fun onMessageReceived(event: MessageEvent) {
        Log.d("PhoneCommunicator", "MessageEvent received. Path = ${event.path}")
        if (event.path == "/sync") {
            val msg = String(event.data)
            Log.d("PhoneCommunicator", "Message decoded: $msg")
            onMessage(msg)
        } else {
            Log.w("PhoneCommunicator", "Unknown path received: ${event.path}")
        }
    }
}
