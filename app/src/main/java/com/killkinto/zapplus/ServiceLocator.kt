package com.killkinto.zapplus

import com.killkinto.zapplus.data.IPropertyRepositoy
import com.killkinto.zapplus.data.PropertyRepositoy
import com.killkinto.zapplus.data.local.PropertyLocalDataSource
import com.killkinto.zapplus.data.remote.PropertyApi
import com.killkinto.zapplus.data.remote.PropertyRemoteDataSource
import com.killkinto.zapplus.data.remote.retrofit

object ServiceLocator {
    private var propertyRepositoy: IPropertyRepositoy? = null

    fun providePropertyRepository(): IPropertyRepositoy {
        return propertyRepositoy ?: createPropertyRepository()
    }

    private fun createPropertyRepository(): IPropertyRepositoy {
        return PropertyRepositoy(
            PropertyLocalDataSource,
            createPropertyRemoteDataSource()
        ).also { this.propertyRepositoy = it }
    }

    private fun createPropertyRemoteDataSource() =
        PropertyRemoteDataSource(PropertyApi(retrofit))
}