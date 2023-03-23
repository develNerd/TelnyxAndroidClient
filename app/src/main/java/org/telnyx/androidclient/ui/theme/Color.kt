package org.telnyx.androidclient.ui.theme

import androidx.compose.ui.graphics.Color

object Colors {
    val colorPrimary = Color(0xFF79CF44)
    val colorSecondary = Color(0xFF51BB68)
    val colorOnSecondary = Color(0xFFFFFFFF)
    val colorLightBackGround = Color(0xFFFFFFFF)
    val colorDarkBackGround = Color(0xFF373A3A)
    val colorTertiary = Color(0xFF616969)
    val colorWarning = Color(0xFFBDC916)
    val colorError= Color(0xFFBB1624)

    fun textColor(isDark: Boolean) = if (isDark)   Color(0xFFFFFFFF) else  Color(0xFF0F0F0F)
}

