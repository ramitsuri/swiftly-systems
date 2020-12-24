package com.ramitsuri.swiftly.data

import com.ramitsuri.swiftly.event.EventType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object LocalEventManager : EventManager {
    private val eventType = MutableStateFlow(EventType.None)

    override fun get(): StateFlow<EventType> {
        return eventType
    }

    override suspend fun add(event: EventType) {
        eventType.value = event
        delay(2000)
        reset()
    }

    private fun reset() {
        eventType.value = EventType.None
    }
}