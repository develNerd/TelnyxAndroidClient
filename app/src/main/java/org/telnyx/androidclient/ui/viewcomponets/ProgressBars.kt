package org.telnyx.androidclient.ui.viewcomponets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import org.telnyx.androidclient.ui.theme.Dimens
import org.telnyx.androidclient.ui.theme.Dimens.spacing16dp

@Composable
fun GenericCircleProgressIndicator(modifier: Modifier = Modifier, color: Color = Color.White){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.testTag("PROGRESS_TEST_TAG")
            .fillMaxWidth()
            .padding(Dimens.mediumPadding)
            .background(Color.Transparent, shape = RoundedCornerShape(0))
    ) {
        CircularProgressIndicator(modifier = Modifier.size(Dimens.size24dp), color = color)
    }
}