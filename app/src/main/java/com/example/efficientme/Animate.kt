package com.example.efficientme

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Animate(onAnimationEnd: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        var showAnimation by remember{ mutableStateOf(false) }
        val transition = updateTransition(targetState = showAnimation, label = "")

        LaunchedEffect(Unit) {
            delay(500)
            showAnimation = true
            delay(3300)
            onAnimationEnd()
        }

        val textEfficientAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 1000) },
            label = "textEfficientAlpha"
        ) { state -> if(state) 1f else 0f }

        val textEfficientOffset by transition.animateDp (
            transitionSpec = { tween(durationMillis = 1000, easing = FastOutLinearInEasing) },
            label = "textEfficientOffset"
        ) { state -> if (state) (-35).dp else (-200).dp }

        val textMeAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 1000) },
            label = "textEfficientAlpha"
        ) { state -> if(state) 1f else 0f }

        val textMeOffset by transition.animateDp (
            transitionSpec = { tween(durationMillis = 1000, easing = FastOutLinearInEasing) },
            label = "textEfficientOffset"
        ) { state -> if (state) 95.dp else (200).dp }

        val textWorthAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 1500, delayMillis = 500) },
            label = "textEfficientAlpha"
        ) { state -> if(state) 1f else 0f }

        val screenOffsetX by transition.animateIntOffset(
            transitionSpec = { tween(durationMillis = 300, delayMillis = 2300, easing = FastOutLinearInEasing) },
            label = "screenOffsetX"
        ) { state -> if (state) IntOffset(-1600, 0) else IntOffset(0, 0) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { screenOffsetX },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.starter_backgroundimage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Efficient",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = (Color(0xFF884DB7)).copy(alpha = textEfficientAlpha),
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.offset(x = textEfficientOffset, y = 0.dp)
                    )

                    Spacer(modifier = Modifier.width(0.dp))

                    Text(
                        text = "Me",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = (Color(0xFF884DB7)).copy(alpha = textMeAlpha),
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.offset(x = textMeOffset, y = 0.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Make It Worth",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = (Color(0xFF91767F)).copy(alpha = textWorthAlpha),
                        fontSize = 30.sp,
                    ),
                    fontFamily = FontFamily(Font(R.font.mynerve_font)),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}