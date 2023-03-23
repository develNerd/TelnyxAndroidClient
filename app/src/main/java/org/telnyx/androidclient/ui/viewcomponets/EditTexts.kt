package org.telnyx.androidclient.ui.viewcomponets

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.telnyx.androidclient.R
import org.telnyx.androidclient.ui.theme.Colors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutLineEdittext(
    modifier: Modifier = Modifier,
    hint: String,
    text: String,
    leadingIcon: Int? = null,
    onTextChanged: (String) -> Unit
) {

    OutlinedTextField(modifier = modifier, value = text, onValueChange = onTextChanged, label = {
        Text(hint, color = Colors.textColor(isDark = isSystemInDarkTheme()))
    }, leadingIcon = {
        if (leadingIcon != null) {
            Image(
                painter = painterResource(id = leadingIcon), contentDescription = stringResource(
                    id = R.string.app_name
                )
            )
        }
    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallEdittext(modifier: Modifier = Modifier, hint:String, text:String, backgroundColor : Color = Color.Transparent, stroke: BorderStroke, onTextChanged:(String) -> Unit){

    Box(modifier = modifier
        .fillMaxWidth()
        ) {

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChanged,
            placeholder = { Text(hint) },
            shape = TextFieldDefaults.outlinedShape,
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordOutLineEdittext(
    modifier: Modifier = Modifier,
    hint: String,
    text: String,
    leadingIcon: Int? = null,
    onTextChanged: (String) -> Unit
) {


    var passwordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier, value = text, onValueChange = onTextChanged,
        label = {
            Text(hint, color =  Colors.textColor(isDark = isSystemInDarkTheme()))
        },
        leadingIcon = {
            if (leadingIcon != null) {
                Image(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(id = if (passwordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                    contentDescription = stringResource(
                        id = R.string.app_name
                    )
                )
            }

        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

}


