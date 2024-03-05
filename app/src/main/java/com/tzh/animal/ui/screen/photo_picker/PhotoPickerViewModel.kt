package com.unsplash.pickerandroid.photopicker.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tzh.animal.domain.PhotoPickerRepository
import com.tzh.animal.ui.navigation.PhotoPickerRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model for the picker screen.
 * This will use the repository to fetch the photos depending on the search criteria.
 * This is using rx binding.
 */
@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
    photoPickerRepository: PhotoPickerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val photosLiveData = photoPickerRepository.getAnimalPhoto(
        savedStateHandle.get<String>(PhotoPickerRoute.argument) ?: ""
    ).cachedIn(viewModelScope)

    fun getTag(): String {
        return PhotoPickerViewModel::class.java.simpleName
    }

}
