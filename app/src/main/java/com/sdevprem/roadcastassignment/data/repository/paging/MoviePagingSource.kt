package com.sdevprem.roadcastassignment.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sdevprem.roadcastassignment.data.datasource.MovieDataSource
import com.sdevprem.roadcastassignment.data.datasource.model.MovieListResponse
import com.sdevprem.roadcastassignment.data.entity.MovieMeta

class MoviesPagingSource(
    private val dataSource: MovieDataSource
) : PagingSource<Int, MovieMeta>() {

    override fun getRefreshKey(state: PagingState<Int, MovieMeta>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieMeta> {
        return try {
            val movies = dataSource.getTopRatedMovieList(
                params.key ?: 1
            )

            LoadResult.Page(
                data = movies.movieList,
                prevKey = if (movies.page == 1) null else movies.page - 1,
                nextKey = if (movies.page == movies.totalPages) null else movies.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}