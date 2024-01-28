package com.mobilebreakero.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentScreen(
    navController: NavController,
    viewModel: HomeViewModel? = hiltViewModel(),
    postId: String
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser
    val comment = remember { mutableStateOf("") }

    GetUserFromFireStore(
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
                .height(500.dp)
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
                text = "Add Comment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
            OutlinedTextField(
                value = comment.value,
                onValueChange = {
                    comment.value = it
                },
                label = { Text(text = "Comment") },
                modifier = Modifier
                    .width(300.dp)
                    .height(110.dp)
                    .padding(10.dp),
                maxLines = 3,
                singleLine = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4F80FF),
                    unfocusedBorderColor = Color(0xFF4F80FF),
                    cursorColor = Color(0xFF4F80FF),
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))

            val context = LocalContext.current
            CommentButton(
                onClick = {
                    user.value.name?.let {
                        user.value.id?.let { it1 ->
                            viewModel?.addComment(
                                postId = postId,
                                comment = comment.value,
                                userName = it1,
                                userId = it,
                            )
                        }
                    }
                    Toast.makeText(context, "Comment Added", Toast.LENGTH_SHORT).show()

                    navController.popBackStack()
                },
                buttonColor = Color(0xFF4F80FF),
                text = "Comment"
            )
        }
    }
}

@Composable
fun CommentButton(
    onClick: () -> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    border: BorderStroke? = null,
    textColor: Color? = Color.White
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        Text(text = text, fontSize = 18.sp, color = textColor!!)
    }
}
