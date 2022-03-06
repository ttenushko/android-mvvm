package com.ttenushko.mvvm.demo.domain.application.usecase

import com.ttenushko.mvvm.demo.domain.usecase.SingleResultUseCase
import com.ttenushko.mvvm.demo.domain.weather.model.Place

interface SavePlaceUseCase : SingleResultUseCase<SavePlaceUseCase.Param, SavePlaceUseCase.Result> {

    data class Param(
        val place: Place
    )

    data class Result(
        val place: Place,
        val isAdded: Boolean
    )
}