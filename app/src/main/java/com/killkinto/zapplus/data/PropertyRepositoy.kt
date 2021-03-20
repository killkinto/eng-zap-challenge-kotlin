package com.killkinto.zapplus.data

import com.killkinto.zapplus.data.model.Property
import com.killkinto.zapplus.util.wrapEspressoIdlingResource

class PropertyRepositoy(
    private val localDataSource: PropertyDataSource,
    private val remoteDataSource: PropertyDataSource
) : IPropertyRepositoy {

    override suspend fun getProperties(portal: String): List<Property> {
        wrapEspressoIdlingResource {
            var properties = localDataSource.getProperties(portal)
            if (properties.isEmpty()) {
                properties = remoteDataSource.getProperties(portal)
                localDataSource.saveProperties(properties)
                properties = localDataSource.getProperties(portal)
            }
            return properties
        }
    }

    override fun getProperty(id: String): Property? {
        return localDataSource.getProperty(id)
    }

    override fun deleteAllProperties() {
        localDataSource.deleteAllProperties()
    }
}