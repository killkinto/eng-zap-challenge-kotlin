package com.killkinto.zapplus.data.local

import com.killkinto.zapplus.data.PropertyDataSource
import com.killkinto.zapplus.data.model.Portal
import com.killkinto.zapplus.data.model.Property

object PropertyLocalDataSource : PropertyDataSource {
    private var properties: List<Property> = mutableListOf()

    override suspend fun getProperties(portal: String): List<Property> {
        return when (portal) {
            Portal.ZAP -> properties.filter { it.isEligibleZap() }
            Portal.VIVA_REAL -> properties.filter { it.isEligibleVivaReal() }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getProperty(id: String) = properties.find { it.id == id }

    override fun deleteAllProperties() {
        properties = mutableListOf()
    }

    override fun saveProperties(properties: List<Property>) {
        this.properties = properties.toMutableList()
    }
}