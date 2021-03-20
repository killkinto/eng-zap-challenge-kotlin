package com.killkinto.zapplus.data.remote

import com.killkinto.zapplus.data.PropertyDataSource
import com.killkinto.zapplus.data.model.Property
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PropertyRemoteDataSource(private val api: PropertyApi) : PropertyDataSource {
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    override suspend fun getProperties(portal: String): List<Property> = withContext(dispatcher) {
        return@withContext try {
            api.retrofitService.getProperties().map { it.asModel() }
        } catch (e: Exception) {
            throw e
        }

    }

    override fun saveProperties(properties: List<Property>) {
        throw UnsupportedOperationException()
    }

    override fun getProperty(id: String): Property? {
        throw UnsupportedOperationException()
    }

    override fun deleteAllProperties() {
        throw UnsupportedOperationException()
    }
}