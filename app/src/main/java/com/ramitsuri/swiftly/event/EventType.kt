package com.ramitsuri.swiftly.event

enum class EventType(val key: String) {
    None("100"),
    ManagerSpecials("101");

    companion object {
        fun fromKey(key: String): EventType {
            for (eventType in values()) {
                if (eventType.key == key) {
                    return eventType
                }
            }
            return None
        }
    }
}