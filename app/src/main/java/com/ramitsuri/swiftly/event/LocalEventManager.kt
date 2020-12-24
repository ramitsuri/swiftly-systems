package com.ramitsuri.swiftly.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocalEventManager : EventManager {
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