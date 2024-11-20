package com.denior.parasol.network.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class City(
    val address: AddressData,
    @SerializedName("display_name")
    val displayName: String,
    val lat: String,
    val lon: String,
    val name: String,
)

data class AddressData(
    val city: String,
    val country: String
)

@Serializable
data class KCity(
    val address: KAddressData,
    @SerialName("display_name")
    val displayName: String,
    val lat: Double,
    val lon: Double,
    val name: String
)

@Serializable
data class KAddressData(
    val city: String,
    val country: String
)
