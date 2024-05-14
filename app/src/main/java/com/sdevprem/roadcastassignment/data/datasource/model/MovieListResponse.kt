package com.sdevprem.roadcastassignment.data.datasource.model

import com.google.gson.annotations.SerializedName
import com.sdevprem.roadcastassignment.data.entity.MovieMeta

data class MovieListResponse(
    @SerializedName("results")
    val movieList: List<MovieMeta>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
)