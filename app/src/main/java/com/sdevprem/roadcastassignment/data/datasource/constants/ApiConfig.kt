package com.sdevprem.roadcastassignment.data.datasource.constants

import com.sdevprem.roadcastassignment.BuildConfig

object ApiConfig {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = BuildConfig.API_KEY
    const val AUTH_QUERY_KEY = "api_key"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
}