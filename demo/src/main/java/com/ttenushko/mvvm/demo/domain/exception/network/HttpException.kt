package com.ttenushko.mvvm.demo.domain.exception.network

import com.ttenushko.mvvm.demo.domain.exception.AppException


class HttpException(
    val httpCode: Int,
    val httpMessage: String? = null,
    exception: Exception? = null
) : AppException(httpMessage, exception)