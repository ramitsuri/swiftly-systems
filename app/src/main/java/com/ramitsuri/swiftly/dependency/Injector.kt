package com.ramitsuri.swiftly.dependency

import com.ramitsuri.swiftly.api.ApiService
import com.ramitsuri.swiftly.data.SpecialsRepository
import com.ramitsuri.swiftly.viewmodel.SpecialsViewModelFactory

class Injector() {
    private val specialsRepository = SpecialsRepository(ApiService.create())

    fun getSpecialsViewModelFactory() = SpecialsViewModelFactory(specialsRepository)
}