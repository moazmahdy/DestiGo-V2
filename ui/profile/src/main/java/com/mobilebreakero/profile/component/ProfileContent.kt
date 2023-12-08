package com.mobilebreakero.profile.component

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.profile.R


@Composable
fun ProfileSection(navController: NavController) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        )
        {
            ProfileImage(
                data = Uri.parse(user.value.photoUrl),
                contentDescription = "profile photo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(
                        CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            NameSection(modifier = Modifier.weight(7f))
        }
        Spacer(modifier = Modifier.height(15.dp))
        DescriptionSection()
        Spacer(modifier = Modifier.height(20.dp))
        CardsDisplay(navController = navController)
    }
}