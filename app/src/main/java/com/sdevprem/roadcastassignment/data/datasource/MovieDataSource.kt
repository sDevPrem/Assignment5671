package com.sdevprem.roadcastassignment.data.datasource

import com.sdevprem.roadcastassignment.data.datasource.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataSource {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(@Query("page") page: Int): MovieListResponse

}