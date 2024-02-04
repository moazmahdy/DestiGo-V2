package com.mobilebreakero.profile.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.core_ui.components.GetUserFromFireStore
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.profile.R
import com.mobilebreakero.profile.account.settings.SettingsViewModel


@Composable
fun ProfileSection(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

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
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
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
        SignUp(
            icon = R.drawable.arrow,
            text = "log out",
            modifier = Modifier.align(CenterHorizontally),
            onClick = {
                viewModel.signOut()
            })
        Spacer(modifier = Modifier.height(10.dp))
    }
}


@Composable
private fun SignUp(icon: Int, text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .width(300.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(.05.dp, Color.Black, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red.copy(alpha = 0.2f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Icon(
                painter = painterResource(id = R.drawable.go),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .padding(5.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
