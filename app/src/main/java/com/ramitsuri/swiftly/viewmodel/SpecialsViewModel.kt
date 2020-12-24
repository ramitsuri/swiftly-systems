package com.ramitsuri.swiftly.viewmodel

import androidx.lifecycle.*
import com.ramitsuri.swiftly.event.EventManager
import com.ramitsuri.swiftly.data.Result
import com.ramitsuri.swiftly.data.SpecialsRepository
import com.ramitsuri.swiftly.entities.ManagerSpecialsResponse
import com.ramitsuri.swiftly.event.EventService
import com.ramitsuri.swiftly.event.EventType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SpecialsViewModel(
    private val repository: SpecialsRepository,
    private val eventManager: EventManager
) : ViewModel() {
    private val eventObserver = Observer<EventType> { type ->
        Timber.i("Event received: $type")
        if (type == EventType.ManagerSpecials) {
            refresh()
        }
    }

    init {
        eventManager.get().observeForever(eventObserver)
        EventService.logToken()
    }

    private val managerSpecials = MutableLiveData<Result<ManagerSpecialsResponse>>()

    fun getManagerSpecials(): LiveData<Result<ManagerSpecialsResponse>> {
        refresh()
        return managerSpecials
    }

    private fun refresh() {
        viewModelScope.launch {
            repository.getManagerSpecials().collect {
                managerSpecials.postValue(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Cleared")
        eventManager.get().removeObserver(eventObserver)
    }
}