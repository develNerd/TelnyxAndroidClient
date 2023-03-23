package org.telnyx.androidclient.ui.viewcomponets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.telnyx.androidclient.ui.theme.Colors
import org.telnyx.androidclient.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnInset(modifier: Modifier = Modifier,verticalArrangement:Arrangement.Vertical = Arrangement.Top, backGround:Color = MaterialTheme.colorScheme.background,topAppBar:@Composable () -> Unit= {},bottomBar:@Composable () -> Unit = {}, content: @Composable () -> Unit){
    Scaffold(topBar = topAppBar, bottomBar = bottomBar) {
        it.calculateBottomPadding()
        it.calculateTopPadding()
        Column(modifier = modifier
            .background(backGround)
            .padding(vertical = Dimens.smallPadding, horizontal = Dimens.mediumPadding), verticalArrangement = verticalArrangement) {
            content()
        }
    }
}