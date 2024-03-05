package com.tzh.animal.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzh.animal.data.NetworkEndpoints

/**
 * Simple repository used as a proxy by the view models to fetch data.
 */
class PhotoPickerRepository(private val networkEndpoints: NetworkEndpoints) {

    fun getAnimalPhoto(criteria: String) = Pager(PagingConfig(pageSize = 100)) {
        SearchPhotoDataSource(networkEndpoints, criteria)
    }.flow

}
