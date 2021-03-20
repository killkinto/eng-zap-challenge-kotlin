package com.killkinto.zapplus.data

import com.killkinto.zapplus.data.model.Property

interface PropertyDataSource {
    /**
     * Obtém os imóveis elegíveis do portal
     */
    suspend fun getProperties(portal: String): List<Property>

    /**
     * Salva os imóveis localmente
     */
    fun saveProperties(properties: List<Property>)

    /**
     * Obtém o imóvel pelo seu ID. Retorna nulo se não for encontrado
     */
    fun getProperty(id: String): Property?

    /**
     * Delete todos os imóveis locais
     */
    fun deleteAllProperties()
}