package com.mobilebreakero.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.model.DataItem
import com.mobilebreakero.auth_domain.model.PhotoDataItem
import com.mobilebreakero.auth_domain.usecase.PhotoUseCase
import com.mobilebreakero.auth_domain.usecase.SearchPlacesUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchPlacesUseCase,
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    private val _searchResult = MutableStateFlow<Response<List<DataItem?>>>(Response.Success(listOf()))
    val searchResult: StateFlow<Response<List<DataItem?>>> = _searchResult

    fun getSearchedResult(query: String, language: String, filter: String) {
        viewModelScope.launch {
            val result = useCase.invoke(
                query = query,
                language = language,
                filter = filter
            )
            _searchResult.value = result
        }
    }

    private val _photo = MutableStateFlow<Response<List<PhotoDataItem?>>>(Response.Success(listOf()))
    val photo: StateFlow<Response<List<PhotoDataItem?>>> = _photo
    var photoResult: Response<List<PhotoDataItem?>> = Response.Success(listOf())

    fun getPhoto(locationId: String) {
        viewModelScope.launch {
            try {
                _photo.value = Response.Loading
                val result = photoUseCase.invoke(locationId)
                _photo.value = result
                photoResult = result
            } catch (e: Exception) {
                _photo.value = Response.Failure(e)
            }
        }
    }

}
