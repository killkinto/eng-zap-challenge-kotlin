package com.killkinto.zapplus.data.remote

import com.killkinto.zapplus.data.model.*
import com.squareup.moshi.Json

data class NetworkPropery(
    val id: String,
    val images: List<String>,
    val address: AddressNetwork?,
    @Json(name = "usableAreas")
    val area: Int,
    val parkingSpaces: Int?,
    val bathrooms: Int,
    val bedrooms: Int,
    val pricingInfos: PricingInfosNetwork
)

data class PricingInfosNetwork(
    val businessType: String,
    val price: Double,
    @Json(name = "yearlyIptu")
    val iptu: Double?,
    @Json(name = "monthlyCondoFee")
    val condominium: String?
)

data class AddressNetwork(
    val city: String?,
    val neighborhood: String?,
    val geoLocation: GeoLocationNetwork?
)

data class GeoLocationNetwork(val location: LocationNetwork?)

data class LocationNetwork(val lon: Double?, val lat: Double?)

fun NetworkPropery.asModel(): Property {
    val businessType = when (this.pricingInfos.businessType) {
        "SALE" -> BusinessType.SALE
        "RENTAL" -> BusinessType.RENTAL
        else -> throw IllegalArgumentException()
    }
    return Property(
        this.id,
        this.images,
        Address(
            this.address?.city ?: "",
            this.address?.neighborhood ?: "",
            GeoLocation(
                this.address?.geoLocation?.location?.lon ?: 0.0,
                this.address?.geoLocation?.location?.lat ?: 0.0
            )
        ),
        Composition(this.area, this.parkingSpaces ?: 0, this.bathrooms, this.bedrooms),
        PricingInfos(
            businessType,
            this.pricingInfos.price,
            this.pricingInfos.iptu ?: 0.0,
            this.pricingInfos.condominium
        )
    )
}

