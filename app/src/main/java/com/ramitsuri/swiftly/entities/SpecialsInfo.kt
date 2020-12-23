package com.ramitsuri.swiftly.entities

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class SpecialsInfo(
    @field:SerializedName("display_name") val displayName: String,
    @field:SerializedName("height") val frameHeight: Int,
    @field:SerializedName("width") val frameWidth: Int,
    @field:SerializedName("price") val newPrice: BigDecimal,
    @field:SerializedName("original_price") val oldPrice: BigDecimal,
    @field:SerializedName("imageUrl") val imageUrl: String
)