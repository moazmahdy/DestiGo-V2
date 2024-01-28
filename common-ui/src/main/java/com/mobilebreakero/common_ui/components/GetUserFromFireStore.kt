package com.mobilebreakero.common_ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.common_ui.MainViewModel
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success

@Composable
fun GetUserFromFireStore(
    viewModel: MainViewModel = hiltViewModel(),
    id: String? = "",
    user: (AppUser) -> Unit,
) {
    LaunchedEffect(viewModel) {
        if (id != null)
            viewModel.getUser(id).collect { userResponse ->
                when (userResponse) {
                    is Success -> {
                        val userData = (userResponse).data
                        DataUtils.user = userData
                        user(userData)
                    }

                    is Failure -> {
                        val exception = (userResponse).e
                        print(exception.message.toString())
                    }

                    Loading -> {
                    }

                    else -> {}
                }
            }
    }
}