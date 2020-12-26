package com.ramitsuri.swiftly.event

import androidx.lifecycle.LiveData

interface EventManager {
    fun get(): LiveData<EventType>
    fun add(event: EventType)
}