package com.ramitsuri.swiftly.viewmodel

import androidx.lifecycle.ViewModel
import com.ramitsuri.swiftly.data.SpecialsRepository

class SpecialsViewModel(
    private val repository: SpecialsRepository
) : ViewModel() {

    fun getManagerSpecials() = repository.getManagerSpecials()
}