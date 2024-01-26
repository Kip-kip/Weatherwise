package com.example.weatherwise.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherwise.presentation.ui.theme.white

@Composable
fun LoadingEffect(
    circleColor: Color =  white,
    animationDelay: Int = 1000
) {

    var circleScale by remember {
        mutableStateOf(0f)
    }

    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )

    LaunchedEffect(Unit) {
        circleScale = 1f
    }

    Row(modifier = Modifier.fillMaxWidth(),Arrangement.Center){

        Box(
            modifier = Modifier
                .size(size = 50.dp)
                .scale(scale = circleScaleAnimate.value)
                .border(
                    width = 4.dp,
                    color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                    shape = CircleShape
                )
        ) {

        }
    }

}

