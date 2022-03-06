package com.ttenushko.mvvm.demo.data.weather.repository.rest.model

class NetErrorResponse(
    code: Int,
    message: String
) : NetBaseResponse(code, message)