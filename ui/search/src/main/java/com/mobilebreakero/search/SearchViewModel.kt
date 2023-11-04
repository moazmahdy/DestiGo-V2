package com.mobilebreakero.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.util.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Response.Loading
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: SearchResultUseCase) : ViewModel() {

    private val search = mutableStateOf(SearchState())

    val searchItems: State<SearchState>
        get() = search

    fun getSearchResultStream(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String
    ) {
        useCase(
            location = location,
            radius = radius,
            type = type,
            language = language,
            keyword = keyword
        ).onEach { dataState ->
            when (dataState) {
                is Success -> {
                    search.value = SearchState(
                        items = dataState.data
                    )
                    Log.e("Search", dataState.data.toString())
                }

                is Failure -> {
                    search.value = SearchState(
                        error = dataState.e.message ?: "An unexpected error happened"
                    )
                    Log.e("Search", dataState.e.message ?: "An unexpected error happened")
                }

                is Loading -> {
                    search.value = SearchState(isLoading = true)
                    Log.e("Search", "Loading..")
                }
            }
        }.launchIn(viewModelScope)
    }
}
