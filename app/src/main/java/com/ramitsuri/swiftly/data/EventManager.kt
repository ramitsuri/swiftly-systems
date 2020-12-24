package com.ramitsuri.swiftly.data

import com.ramitsuri.swiftly.event.EventType
import kotlinx.coroutines.flow.StateFlow

interface EventManager {
    fun get(): StateFlow<EventType>
    suspend fun add(event: EventType)
}