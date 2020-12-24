package com.ramitsuri.swiftly.data

import com.ramitsuri.swiftly.api.ApiService
import com.ramitsuri.swiftly.entities.ManagerSpecialsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class SpecialsRepository(private val api: ApiService) {

    private var cache: ManagerSpecialsResponse? = null

    fun getManagerSpecials(): Flow<Result<ManagerSpecialsResponse>> {
        return flow<Result<ManagerSpecialsResponse>> {
            emit(Result.loading())
            cache?.let {
                emit(Result.success(it))
            } ?: run {
                val response = api.get()
                if (response.isSuccessful && response.body() != null) {
                    val managerSpecialsResponse = response.body()!!
                    cache = managerSpecialsResponse
                    emit(Result.success(managerSpecialsResponse))
                } else {
                    emit(Result.error(response.message()))
                }
            }
        }.catch {
            emit(Result.error("Network error!"))
            Timber.w(it)
        }.flowOn(Dispatchers.IO)
    }
}