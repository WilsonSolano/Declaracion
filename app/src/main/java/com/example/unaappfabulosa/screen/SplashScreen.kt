package com.example.unaappfabulosa.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.unaappfabulosa.R
import com.example.unaappfabulosa.ui.theme.NunitoFamily
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Star(
    var x: Float,
    var y: Float,
    val size: Float,
    var alpha: Float
)

@Composable
fun AnimatedStarsBackground() {
    val stars = remember {
        List(30) {
            mutableStateOf(
                Star(
                    x = Random.nextFloat(),
                    y = 1f + Random.nextFloat(),
                    size = Random.nextFloat() * 3f + 1f,
                    alpha = Random.nextFloat() * 0.5f + 0.1f
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            stars.forEach { starState ->
                val star = starState.value
                star.y -= 0.001f
                if (star.y < -0.1f) {
                    star.y = 1.1f
                    star.x = Random.nextFloat()
                    star.alpha = Random.nextFloat() * 0.5f + 0.1f
                }
                starState.value = star.copy()
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        stars.forEach { starState ->
            val star = starState.value
            drawCircle(
                color = Color.White.copy(alpha = star.alpha),
                radius = star.size.dp.toPx(),
                center = Offset(
                    x = star.x * size.width,
                    y = star.y * size.height
                ),
                blendMode = BlendMode.Plus
            )
        }
    }
}

@Composable
fun SplashScreen(onNavigateToProposal: () -> Unit = {}) {
    var dragOffset by remember { mutableStateOf(0f) }
    var totalDrag by remember { mutableStateOf(0f) }

    val animatedArrowOffset by animateFloatAsState(
        targetValue = if (dragOffset < 0f) 20f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF286BB1),
                        Color(0xFF26386F),
                        Color(0xFF040404)
                    )
                )
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = {
                        totalDrag = 0f
                    },
                    onVerticalDrag = { _, dragAmount ->
                        totalDrag += dragAmount
                        dragOffset = totalDrag
                    },
                    onDragEnd = {
                        if (totalDrag < -200f) {
                            onNavigateToProposal()
                        }
                        dragOffset = 0f
                        totalDrag = 0f
                    }
                )
            }
    ) {
        AnimatedStarsBackground()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Dada la inmensidad del tiempo y la inmensidad del universo, es un placer inmenso compartir un planeta y un tiempo contigo",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 38.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )
        }

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.atardecer))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Desliza hacia arriba",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.3f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Swipe up",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .offset(y = with(LocalDensity.current) { animatedArrowOffset.dp })
                    .alpha(0.8f)
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SimpleComposablePreview() {
    SplashScreen()
}
