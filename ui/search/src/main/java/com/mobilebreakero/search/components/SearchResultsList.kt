package com.mobilebreakero.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.search.SearchViewModel


@Composable
fun SearchResultsList(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {

    val searchResults by viewModel.searchResult.collectAsState()
    val photoResults by viewModel.photo.collectAsState()
    val requestedLocationIds = remember { mutableSetOf<String>() }

    LazyColumn(
        modifier = Modifier.height(400.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val results = when (val response = searchResults) {
            is Response.Success -> response.data
            else -> emptyList()
        }

        val photos = when (val response = photoResults) {
            is Response.Success -> response.data
            else -> null
        }

        items(results.size) { index ->
            val result = results[index]

            if (result?.locationId !in requestedLocationIds) {
                result?.locationId?.let { viewModel.getPhoto(it) }
                requestedLocationIds.add(result?.locationId ?: "")
            }
            val randomPhoto = if ((photos?.size ?: 0) > 0) photos?.random() else null

            val photoOfEachItem =
                if (index < (photos?.size ?: 0)) photos?.get(index) else randomPhoto

            if (result != null) {
                SearchResultItem(
                    item = result,
                    navController = navController,
                    photoDataItem = photoOfEachItem
                )
            }
        }
    }
}