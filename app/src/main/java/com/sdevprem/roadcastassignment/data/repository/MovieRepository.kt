package com.sdevprem.roadcastassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sdevprem.roadcastassignment.data.datasource.MovieDataSource
import com.sdevprem.roadcastassignment.data.entity.MovieMeta
import com.sdevprem.roadcastassignment.data.repository.paging.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDataSource: MovieDataSource
) {

    fun getTopRatedMovies(): Flow<PagingData<MovieMeta>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { MoviesPagingSource(movieDataSource) }
        ).flow
    }
}