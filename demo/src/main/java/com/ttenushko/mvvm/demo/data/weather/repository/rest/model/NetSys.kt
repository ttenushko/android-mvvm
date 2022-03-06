package com.ttenushko.mvvm.demo.data.weather.repository.rest.model

import com.google.gson.annotations.SerializedName

data class NetSys(
    @field:SerializedName("country") val countryCode: String
)