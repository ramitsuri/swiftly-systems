package com.ramitsuri.swiftly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramitsuri.swiftly.event.EventManager
import com.ramitsuri.swiftly.data.SpecialsRepository

class SpecialsViewModelFactory(
    private val repository: SpecialsRepository,
    private val eventManager: EventManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SpecialsRepository::class.java,
            EventManager::class.java
        ).newInstance(repository, eventManager)
    }
}