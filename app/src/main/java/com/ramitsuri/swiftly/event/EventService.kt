package com.ramitsuri.swiftly.event

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ramitsuri.swiftly.App
import timber.log.Timber

class EventService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data[EVENT_TYPE_KEY]?.let { value ->
            val eventType: EventType = EventType.fromKey(value)
            onEventReceived(eventType)
        }
    }

    private fun onEventReceived(eventType: EventType) {
        App.instance.injector.getEventManager().add(eventType)
    }

    override fun onNewToken(token: String) {
        Timber.i("Refreshed token: $token")
    }

    companion object {
        private const val EVENT_TYPE_KEY = "eventType"

        fun logToken() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.i("Token: ${task.result}")
                } else {
                    Timber.i("Token not available")
                }
            }
        }
    }
}