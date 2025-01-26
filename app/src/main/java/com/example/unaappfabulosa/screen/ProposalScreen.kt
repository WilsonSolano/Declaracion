package com.example.unaappfabulosa.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.unaappfabulosa.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun GalaxyBackground() {
    data class StarD(
        val x: Float,
        val y: Float,
        val size: Float,
        val baseAlpha: Float,
        val speed: Float,
        val color: Color,
        val orbitRadius: Float = Random.nextFloat() * 250f,
        val angle: Float = Random.nextFloat() * 2 * Math.PI.toFloat(),
        val phaseOffset: Float = Random.nextFloat() * 2 * Math.PI.toFloat(),
        val lifePhase: Float = Random.nextFloat(),
        val homeX: Float = Random.nextFloat(),
        val homeY: Float = Random.nextFloat(),
        val introDelay: Float = Random.nextFloat()
    )

    var stars by remember { mutableStateOf(emptyList<StarD>()) }
    val infiniteTransition = rememberInfiniteTransition()

    val animationPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 25000
                0f at 0
                3.8f * Math.PI.toFloat() at 20000
                4f * Math.PI.toFloat() at 25000
            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val blinkPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = CubicBezierEasing(0.4f, 0.0f, 0.6f, 1.0f)),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        stars = List(350) {
            StarD(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 6f,
                baseAlpha = Random.nextFloat().coerceIn(0.4f, 1f),
                speed = Random.nextFloat() * 0.06f + 0.03f,
                color = when (Random.nextInt(7)) {
                    0 -> Color(0xFFAAC5FF)
                    1 -> Color(0xFFFFC1E0)
                    2 -> Color(0xFF89FFC6)
                    3 -> Color(0xFFFFF689)
                    4 -> Color(0xFFC4A5FF)
                    5 -> Color(0xFFFFAB91)
                    else -> Color.White.copy(alpha = 0.8f)
                }
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val totalPhase = 4 * Math.PI.toFloat()
        val transitionProgress = animationPhase / totalPhase
        val fadeOutProgress = (transitionProgress - 0.85f).coerceIn(0f, 1f) * 6.66f
        val fadeInProgress = (1.25f - transitionProgress * 1.25f).coerceIn(0f, 1f)

        stars.forEach { star ->
            val introFactor = (1f - star.introDelay).coerceIn(0f, 1f)
            val lifeFactor = when {
                fadeOutProgress > 0 -> 1f - (fadeOutProgress * star.lifePhase.pow(2))
                fadeInProgress > 0 -> fadeInProgress * (1f - star.lifePhase).pow(0.5f) * introFactor
                else -> 1f
            }

            val convergenceFactor = if (animationPhase > 3.7f * Math.PI) {
                (totalPhase - animationPhase) / (0.3f * Math.PI.toFloat())
            } else 1f

            val currentX = star.homeX +
                    cos(star.angle + animationPhase * star.speed + star.phaseOffset) *
                    star.orbitRadius * convergenceFactor * lifeFactor / size.width

            val currentY = star.homeY +
                    sin(star.angle + animationPhase * star.speed + star.phaseOffset) *
                    star.orbitRadius * convergenceFactor * lifeFactor / size.height

            val alphaWave = (sin(blinkPhase * 1.8f + star.phaseOffset * 2f) + 1f) / 2.5f
            val sizeWave = (cos(blinkPhase * 2.2f + star.phaseOffset * 3f) + 1f) * 0.4f
            val dynamicAlpha = (star.baseAlpha * alphaWave * lifeFactor).coerceIn(0.1f, 0.95f)
            val dynamicSize = star.size * (1f + sizeWave) * lifeFactor.coerceIn(0.6f, 1.2f)

            drawCircle(
                color = star.color.copy(alpha = dynamicAlpha),
                radius = dynamicSize.dp.toPx(),
                center = Offset(
                    x = currentX * size.width,
                    y = currentY * size.height
                )
            )

            if (fadeOutProgress > 0.5f) {
                val trailAlpha = (1f - fadeOutProgress) * 0.4f
                val trailSize = dynamicSize * 2.5f
                drawCircle(
                    color = star.color.copy(alpha = trailAlpha),
                    radius = trailSize.dp.toPx(),
                    center = Offset(
                        x = star.homeX * size.width,
                        y = star.homeY * size.height
                    ),
                    blendMode = BlendMode.Plus
                )
            }
        }

        if (fadeOutProgress > 0) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.4f * (1f - fadeOutProgress)),
                        Color.Transparent
                    ),
                    center = center,
                    radius = size.minDimension * 0.5f
                ),
                radius = size.maxDimension * fadeOutProgress * 1.8f,
                center = center,
                blendMode = BlendMode.Screen
            )
        }
    }
}

@Composable
fun ProposalScreen(
    onAccept: () -> Unit = {},
) {
    var noButtonOffset by remember { mutableStateOf(Offset.Zero) }
    var showHeartAnimation by remember { mutableStateOf(false) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }
    val hapticFeedback = LocalHapticFeedback.current

    // Lottie Animation
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.corazon)
    )

    LaunchedEffect(showHeartAnimation) {
        if (showHeartAnimation) {
            delay(2000)
            onAccept()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A043C))
    ) {
        GalaxyBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¿Aceptas ser mi\ncompañera estelar?",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    shadow = Shadow(
                        color = Color(0xFF7C4DFF),
                        offset = Offset(4f, 4f),
                        blurRadius = 12f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val maxWidth = constraints.maxWidth
                    val maxHeight = constraints.maxHeight

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                showHeartAnimation = true
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .height(56.dp)
                                .shadow(16.dp, shape = MaterialTheme.shapes.extraLarge),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF7C4DFF),
                                contentColor = Color.White
                            ),
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Aceptar",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Sí", fontSize = 18.sp)
                        }

                        // boton No con movimiento aleatorio
                        Box(
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            val maxX = maxWidth - buttonSize.width
                                            val maxY = maxHeight - buttonSize.height

                                            noButtonOffset = Offset(
                                                Random.nextFloat() * maxX,
                                                Random.nextFloat() * maxY
                                            )

                                            hapticFeedback.performHapticFeedback(
                                                HapticFeedbackType.LongPress
                                            )

                                            tryAwaitRelease()
                                        }
                                    )
                                }
                                .onGloballyPositioned {
                                    buttonSize = it.size
                                }
                        ) {
                            val animatedOffset by animateOffsetAsState(
                                targetValue = noButtonOffset,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )

                            Button(
                                onClick = {
                                    val safeWidth = maxWidth - buttonSize.width
                                    val safeHeight = maxHeight - buttonSize.height

                                    noButtonOffset = Offset(
                                        Random.nextFloat() * safeWidth,
                                        Random.nextFloat() * safeHeight
                                    )
                                },
                                modifier = Modifier
                                    .offset {
                                        IntOffset(
                                            animatedOffset.x.toInt(),
                                            animatedOffset.y.toInt()
                                        )
                                    }
                                    .width(120.dp)
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF424242),
                                    contentColor = Color.White
                                ),
                                shape = MaterialTheme.shapes.extraLarge
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Rechazar",
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("No", fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }

        if (showHeartAnimation) {
            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SimpleComposablePreview2() {
    GalaxyBackground()
}