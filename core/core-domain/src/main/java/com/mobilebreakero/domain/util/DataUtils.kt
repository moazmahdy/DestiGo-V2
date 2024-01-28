package com.mobilebreakero.domain.util

import com.mobilebreakero.domain.BuildConfig
import com.mobilebreakero.domain.model.AppUser


object DataUtils {

    var user: AppUser? = null

    const val API_KEY = BuildConfig.MAPS_API_KEY

    const val TRIP_API_KEY = BuildConfig.TRIP_API_KEY
    const val BASE_URL = "https://api.content.tripadvisor.com/api/v1/"

}