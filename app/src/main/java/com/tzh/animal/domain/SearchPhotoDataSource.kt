package com.tzh.animal.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tzh.animal.data.NetworkEndpoints
import com.tzh.animal.ulti.UnsplashPhotoPicker
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

/**
 * Android paging library data source.
 * This will load the photos for the search and allow an infinite scroll on the picker screen.
 */

class SearchPhotoDataSource(
    private val networkEndpoints: NetworkEndpoints, private val criteria: String
) : PagingSource<Int, UnsplashPhoto>() {

    private var lastPage = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        return try {
            val response = networkEndpoints.searchPhotos(
                UnsplashPhotoPicker.getAccessKey(), criteria, params.key ?: 1, params.loadSize
            )

            if (response.isSuccessful) {
                val results = response.body()?.results ?: emptyList()
                val nextPage = if (results.isEmpty()) 0 else (params.key ?: 1) + 1
                lastPage = nextPage
                LoadResult.Page(
                    data = results, prevKey = null, nextKey = lastPage
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int {
        // If there are items in the PagingState, return the key of the first item
        return state.anchorPosition ?: 1
    }
}
