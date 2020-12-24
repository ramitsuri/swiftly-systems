package com.ramitsuri.swiftly.dependency

import com.ramitsuri.swiftly.api.ApiService
import com.ramitsuri.swiftly.data.EventManager
import com.ramitsuri.swiftly.data.LocalEventManager
import com.ramitsuri.swiftly.data.SpecialsRepository
import com.ramitsuri.swiftly.utils.CurrencyFormatter
import com.ramitsuri.swiftly.viewmodel.SpecialsViewModelFactory
import java.util.*

class Injector() {
    private val specialsRepository = SpecialsRepository(ApiService.create())

    fun getEventManager(): EventManager = LocalEventManager
    fun getSpecialsViewModelFactory() =
        SpecialsViewModelFactory(specialsRepository, getEventManager())

    fun getCurrencyFormatter() = CurrencyFormatter(Locale.getDefault())
}