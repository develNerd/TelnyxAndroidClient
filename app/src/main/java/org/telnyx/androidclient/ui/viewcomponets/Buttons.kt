package org.telnyx.androidclient.ui.viewcomponets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.telnyx.androidclient.ui.theme.Colors
import org.telnyx.androidclient.ui.theme.Dimens

@Composable
fun RoundedCornerButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    borderStroke: Dp = 1.dp,
    borderColor: Color = contentColor,
    enabled:Boolean = true,
    isLoading:Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor, contentColor),
        shape = MaterialTheme.shapes.small,
        enabled= enabled,
        modifier = modifier, border = BorderStroke(borderStroke,borderColor)
    ) {
        if (isLoading){
            GenericCircleProgressIndicator()
        }else{
            MediumTextBold(text = text, modifier = Modifier.padding(Dimens.smallPadding), color = contentColor)
        }
    }
}