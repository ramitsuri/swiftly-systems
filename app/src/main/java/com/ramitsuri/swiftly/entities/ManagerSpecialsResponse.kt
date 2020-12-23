package com.ramitsuri.swiftly.entities

import com.google.gson.annotations.SerializedName

data class ManagerSpecialsResponse(
    @field:SerializedName("canvasUnit") val canvasUnit: Int,
    @field:SerializedName("managerSpecials") val specials: List<SpecialsInfo>,
)