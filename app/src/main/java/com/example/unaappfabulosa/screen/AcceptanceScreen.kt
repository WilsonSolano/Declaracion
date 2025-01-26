package com.example.unaappfabulosa.screen

import android.graphics.fonts.FontStyle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.unaappfabulosa.R
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AcceptanceScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val heartFloat by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A1B5B),
                        Color(0xFF462B7D),
                        Color(0xFF040404)
                    )
                )
            )
    ) {
        // Lottie de corazones flotantes de fondo
        val heartComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.corazonup))
        LottieAnimation(
            composition = heartComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    alpha = 0.7f
                    translationY = heartFloat
                }
        )

        var textVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(500)
            textVisible = true
        }

        AnimatedVisibility(
            visible = textVisible,
            enter = fadeIn() + slideInVertically(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Sacas una idea de ahí,\n" +
                            "un sentimiento del otro estante,\n" +
                            "lo atas con ayuda de las palabras\n" +
                            "y resulta que te quiero.",
                    fontSize = 18.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(2f, 2f),
                            blurRadius = 8f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Total parcial:",
                        fontSize = 16.sp,
                        color = Color(0xFFFFB3E0),
                    )

                    Text(
                        text = "te quiero",
                        fontSize = 28.sp,
                        color = Color(0xFFFF69B4),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            shadow = Shadow(
                                color = Color.Magenta.copy(alpha = 0.3f),
                                blurRadius = 16f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Total general:",
                        fontSize = 18.sp,
                        color = Color(0xFFFFB3E0),
                    )

                    Text(
                        text = "te amo",
                        fontSize = 36.sp,
                        color = Color(0xFFFF1493),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            shadow = Shadow(
                                color = Color.Red.copy(alpha = 0.2f),
                                blurRadius = 24f
                            )
                        )
                    )
                }
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            repeat(50) {
                drawCircle(
                    color = Color.White.copy(alpha = Random.nextFloat() * 0.3f),
                    radius = Random.nextFloat() * 3.dp.toPx(),
                    center = Offset(
                        x = Random.nextFloat() * size.width,
                        y = Random.nextFloat() * size.height
                    )
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x33FF69B4),
                        Color.Transparent
                    ),
                    center = center,
                    radius = size.maxDimension * 0.8f
                ),
                blendMode = BlendMode.Screen
            )
        }
    }
}

@Preview
@Composable
fun AcceptanceScreenPreview() {
    AcceptanceScreen()
}