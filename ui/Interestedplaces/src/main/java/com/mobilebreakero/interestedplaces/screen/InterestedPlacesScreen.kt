package com.mobilebreakero.interestedplaces.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.navigation_core.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.interestedplaces.InterestedPlacesViewModel
import com.mobilebreakero.interestedplaces.components.GreetingSection
import com.mobilebreakero.interestedplaces.components.VerticalGrid
import com.mobilebreakero.interestedplaces.components.selectedItemsNames


@Composable
fun InterestedPlacesScreen(navController: NavController) {

    InterestedScreenContent(navController = navController)

}

@Composable
fun InterestedScreenContent(navController: NavController, viewModel: InterestedPlacesViewModel = hiltViewModel()) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    com.mobilebreakero.core_ui.components.GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        GreetingSection(name = user.value.name)
        Spacer(modifier = Modifier.height(10.dp))
        VerticalGrid()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            com.mobilebreakero.core_ui.components.DestiGoButton(
                onClick = {
                    viewModel.addInterestedPlacesIntoDatasource(
                        user.value.id ?: "",
                        selectedItemsNames
                    )
                    navController.navigate(HOME_SCREEN)
                },
                buttonColor = Color(0xff4F80FF),
                text = "Continue",
                modifier = Modifier
                    .shadow(elevation = 0.dp, shape = CircleShape)
                    .width(270.dp)
                    .height(50.dp)
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp, vertical = 2.dp)
            )
        }

    }
}
