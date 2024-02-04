package com.mobilebreakero.ui.common.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.mobilebreakero.core_ui.design_system.Borders.roundedCornerShape20
import com.mobilebreakero.core_ui.design_system.Colors.colorTransparent
import com.mobilebreakero.core_ui.design_system.Colors.inputFieldContainerColor
import com.mobilebreakero.core_ui.design_system.Colors.mainTextColor
import com.mobilebreakero.core_ui.design_system.MainHeight.mainHeight60
import com.mobilebreakero.core_ui.design_system.MainWidth.largeWidth350
import com.mobilebreakero.core_ui.design_system.Modifiers.fillMaxSize
import com.mobilebreakero.core_ui.design_system.Dimens.large20
import com.mobilebreakero.core_ui.design_system.Dimens.medium12
import com.mobilebreakero.ui.R
import com.mobilebreakero.ui.common.components.TextFieldModifier.tfMainModifier

object TextFieldModifier {
    val tfMainModifier = Modifier
        .width(largeWidth350)
        .height(mainHeight60)
        .padding(horizontal = large20, vertical = medium12)
        .shadow(
            elevation = 5.dp,
            shape = roundedCornerShape20,
        )
}

@Composable
fun DestiGoTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String,
    isEmail: Boolean = false,
) {

    var textFieldText by remember { mutableStateOf(text) }
    var isTextFieldError by remember { mutableStateOf(false) }

    val keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Text
    val placeHolder = if (isEmail) "youremail1234@gmail.com" else label

    isTextFieldError = if (isEmail) checkEmailFormat(textFieldText) else false

    TextField(
        value = textFieldText,
        onValueChange = {
            textFieldText = it
            onValueChange(it)
        },
        modifier = tfMainModifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = roundedCornerShape20,
        colors = TextFieldDefaults.colors(
            disabledTextColor = colorTransparent,
            focusedContainerColor = inputFieldContainerColor,
            unfocusedContainerColor = inputFieldContainerColor,
            disabledContainerColor = inputFieldContainerColor,
            focusedIndicatorColor = mainTextColor,
            unfocusedIndicatorColor = colorTransparent,
            disabledIndicatorColor = colorTransparent,
        ),
        label = { Text(text = label) },
        placeholder = {
            Text(
                text = placeHolder,
                modifier = fillMaxSize,
                textAlign = Center
            )
        },
        maxLines = 1
    )
}

fun checkEmailFormat(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}

fun checkPasswordFormat(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}\$"
    return password.matches(passwordPattern.toRegex())
}

@Composable
fun PasswordTextField(
    onValueChange: (String) -> Unit,
) {

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    isPasswordError = checkPasswordFormat(password)

    TextField(
        value = password,
        onValueChange = {
            password = it
            onValueChange(it)
        },
        isError = isPasswordError,
        label = { Text("Password") },
        modifier = tfMainModifier,
        shape = roundedCornerShape20,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = inputFieldContainerColor,
            disabledTextColor = colorTransparent,
            focusedIndicatorColor = mainTextColor,
            unfocusedIndicatorColor = colorTransparent,
            disabledIndicatorColor = colorTransparent,
        ),
        singleLine = true,
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                painterResource(R.drawable.visible)
            else painterResource(id = R.drawable.visiblty)

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, description)
            }

            if (isPasswordError) {
                Text(
                    text = "Password must contain at least 8 characters, including uppercase, lowercase, and numbers",
                    color = Color.Red
                )
            }
        },
        maxLines = 1
    )
}
