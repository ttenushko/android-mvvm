package com.ttenushko.mvvm.demo.data.weather.repository.rest.model

import com.google.gson.annotations.SerializedName

open class NetBaseResponse(
    @field:SerializedName("cod") val code: Int,
    @field:SerializedName("message") val message: String
)