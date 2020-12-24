package com.ramitsuri.swiftly.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramitsuri.swiftly.event.EventType

object LocalEventManager : EventManager {
    private val eventType = MutableLiveData<EventType>()

    override fun get(): LiveData<EventType> {
        return eventType
    }

    override fun add(event: EventType) {
        if (eventType.hasActiveObservers()) {
            eventType.postValue(event)
        }
    }
}