package com.ramitsuri.swiftly.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MockEventManager : EventManager {
    private val eventType = MutableLiveData<EventType>()

    init {
        GlobalScope.launch {
            while (true) {
                val delayDuration = (10..20).random() * 1000L
                Timber.i("Delaying for ${delayDuration}ms")
                delay(delayDuration)
                val event = EventType.values()[EventType.values().indices.random()]
                Timber.i("Posting event: $event")
                add(event)
            }
        }
    }

    override fun get(): LiveData<EventType> {
        return eventType
    }

    override fun add(event: EventType) {
        if (eventType.hasActiveObservers()) {
            eventType.postValue(event)
        }
    }
}