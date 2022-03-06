package com.ttenushko.mvvm.demo.domain.exception.api

import com.ttenushko.mvvm.demo.domain.exception.AppException


class OpenWeatherMapApiException(
    val code: Int,
    val apiMessage: String? = null,
    exception: Exception? = null
) : AppException(apiMessage, exception)