package com.killkinto.zapplus.data.model

data class Property(
    val id: String,
    val images: List<String>,
    val address: Address,
    val composition: Composition,
    val infos: PricingInfos
) {
    fun isEligibleZap(): Boolean {
        val minSale = 600_000.0
        val minRental = 3_500.0
        return containsGeoLocation()
                && validateAreaForPortalZap()
                && when (infos.businessType) {
            BusinessType.RENTAL -> infos.price >= minRental
            BusinessType.SALE -> infos.price >= if (isInsideBoundingBox()) minSale * 0.9 else minSale
        }
    }

    /**
     * O valor do metro quadrado (chave usableAreas) não pode ser menor/igual a R$ 3.500,00
     * - apenas considerando imóveis que tenham area acima de 0 (imóveis com areas = 0 não são elegíveis).
     */
    private fun validateAreaForPortalZap(): Boolean {
        if (composition.area != 0) {
            val value = calculateSquareMeterValue()
            return value == 0.0 || value > 3_500.00
        }
        return false
    }

    private fun calculateSquareMeterValue() =
        if (infos.businessType == BusinessType.SALE
        ) {
            infos.price / composition.area
        } else 0.0

    /**
     * Verifica se o imóvel está dentro do bounding box dos arredores do Grupo ZAP
     */
    private fun isInsideBoundingBox(): Boolean {
        val lon = address.geoLocation.lon
        val lat = address.geoLocation.lat
        return lon >= BoundingBox.minlon && lon <= BoundingBox.maxlon
                && lat >= BoundingBox.minlat && lat <= BoundingBox.maxlat
    }

    fun isEligibleVivaReal(): Boolean {
        val maxSale = 700_000.0
        val maxRental = 4_000.0
        return containsGeoLocation()
                && isCondominiumValid()
                && when (infos.businessType) {
            BusinessType.RENTAL -> infos.price <=
                    if (isInsideBoundingBox()) maxRental * 1.5 else maxRental
            BusinessType.SALE -> infos.price <= maxSale
        }
    }

    /**
     * O valor do condomínio não pode ser maior/igual que 30% do valor do aluguel
     * - apenas aplicado para imóveis que tenham um condomínio válido e numérico
     * (imóveis com condomínio não numérico ou inválido não são elegíveis).
     */
    private fun isCondominiumValid(): Boolean {
        if (infos.condominium != null) {
            return try {
                val condominiumValue = infos.condominium.toDouble()
                infos.businessType == BusinessType.SALE || condominiumValue < (infos.price * 0.3)
            } catch (nfe: NumberFormatException) {
                return false
            }
        }
        return false
    }

    /**
     * Um imóvel não é elegível em NENHUM PORTAL se este tem lat e lon iguais a 0.
     */
    private fun containsGeoLocation() =
        address.geoLocation.lon != 0.0 || address.geoLocation.lat != 0.0
}

data class Composition(
    val area: Int, val parkingSpaces: Int, val bathrooms: Int,
    val bedrooms: Int
)

data class PricingInfos(
    val businessType: BusinessType,
    val price: Double,
    val iptu: Double,
    val condominium: String?
)

data class Address(val city: String, val neighborhood: String, val geoLocation: GeoLocation)

data class GeoLocation(val lon: Double, val lat: Double)

enum class BusinessType {
    SALE, RENTAL
}

object BoundingBox {
    const val minlon = -46.693419
    const val minlat = -23.568704
    const val maxlon = -46.641146
    const val maxlat = -23.546686
}
