package com.ramitsuri.swiftly.event

import androidx.lifecycle.LiveData
import com.ramitsuri.swiftly.event.EventType

interface EventManager {
    fun get(): LiveData<EventType>
    fun add(event: EventType)
}