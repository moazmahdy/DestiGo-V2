package com.mobilebreakero.profile.account.settings

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.mobilebreakero.navigation_core.NavigationRoutes.PROFILE_SETTINGS
import com.mobilebreakero.navigation_core.NavigationRoutes.SIGN_IN_BEFORE_UPDATE
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.profile.R
import com.mobilebreakero.profile.component.ProfileImage

@Composable
fun AccountSettingsContent(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }

    val firebaseUser = Firebase.auth.currentUser

    com.mobilebreakero.core_ui.components.GetUserFromFireStore(
        user = { uId ->
            uId.id = firebaseUser?.uid
            user.value = uId
        },
        id = firebaseUser?.uid ?: "",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(550.dp)
                .width(390.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .background(Color(0xFFF8FAFF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = user.value.name ?: "",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(
                icon = R.drawable.pofile,
                text = "profile settings",
                onClick = { navController.navigate(PROFILE_SETTINGS) })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.privacy, text = "account access", onClick = {
                navController.navigate(
                    SIGN_IN_BEFORE_UPDATE
                )
            })
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.switchaccount, text = "switch account", onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.feedback, text = "feedback & suggestions", onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.info, text = "help & support", onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.arrow, text = "log out", onClick = {
                viewModel.signOut()
            })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(bottom = 10.dp, top = 30.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfileImage(
                data = Uri.parse(user.value.photoUrl),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .align(CenterVertically)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SettingsCard(icon: Int, text: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Color(0xFF4F80FF),
                modifier = Modifier
                    .height(25.dp)
                    .width(25.dp)
                    .align(CenterVertically)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(CenterVertically)
            )
            Icon(
                painter = painterResource(id = R.drawable.go),
                contentDescription = "",
                tint = Color(0xFF4F80FF),
                modifier = Modifier
                    .height(25.dp)
                    .width(25.dp)
                    .align(CenterVertically)
            )
        }
    }
}
