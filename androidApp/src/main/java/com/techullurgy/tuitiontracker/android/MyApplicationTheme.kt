package com.techullurgy.tuitiontracker.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.tuitiontracker.android.theme.darkColors1
import com.techullurgy.tuitiontracker.android.theme.lightColors1

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) {
//        darkColorScheme(
//            primary = Color(0xFFffb873),
//            onPrimary = Color(0xff4b2800),
//            primaryContainer = Color(0xff6a3b00),
//            onPrimaryContainer = Color(0xffffdcbf),
//            secondary = Color(0xffe2c0a4),
//            onSecondary = Color(0xff412c18),
//            secondaryContainer = Color(0xff59422d),
//            onSecondaryContainer = Color(0xffffdcbf),
//            tertiary = Color(0xffc1cc99),
//            onTertiary = Color(0xff2b340f),
//            tertiaryContainer = Color(0xff424b23),
//            onTertiaryContainer = Color(0xffdde8b3),
//            error = Color(0xffffb4ab),
//            onError = Color(0xff690005),
//            errorContainer = Color(0xff93000a),
//            onErrorContainer = Color(0xffffdad6),
//            background = Color(0xff002114),
//            onBackground = Color(0xff8bf8c4),
//            surface = Color(0xff002114),
//            onSurface = Color(0xff8bf8c4),
//            surfaceVariant = Color(0xff51443a),
//            onSurfaceVariant = Color(0xffd5c3b6),
//            outline = Color(0xff9e8e81)
//        )
//    } else {
//        lightColorScheme(
//            primary = Color(0xff8c5000),
//            onPrimary = Color(0xffffffff),
//            primaryContainer = Color(0xffffdcbf),
//            onPrimaryContainer = Color(0xff2d1600),
//            secondary = Color(0xff735943),
//            onSecondary = Color(0xffffffff),
//            secondaryContainer = Color(0xffffdcbf),
//            onSecondaryContainer = Color(0xff291806),
//            tertiary = Color(0xff596339),
//            onTertiary = Color(0xffffffff),
//            tertiaryContainer = Color(0xffdde8b3),
//            onTertiaryContainer = Color(0xff171e00),
//            error = Color(0xffba1a1a),
//            onError = Color(0xffffffff),
//            errorContainer = Color(0xffffdad6),
//            onErrorContainer = Color(0xff410002),
//            background = Color(0xfff4fff6),
//            onBackground = Color(0xff002114),
//            surface = Color(0xfff4fff6),
//            onSurface = Color(0xff002114),
//            surfaceVariant = Color(0xfff2dfd1),
//            onSurfaceVariant = Color(0xff51443a),
//            outline = Color(0xff837469)
//        )
//    }

    val colors = if(darkTheme) {
        darkColors1
    } else {
        lightColors1
    }

    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
