package com.ramitsuri.swiftly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramitsuri.swiftly.data.SpecialsRepository

class SpecialsViewModelFactory(
    private val repository: SpecialsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SpecialsRepository::class.java
        ).newInstance(repository)
    }
}