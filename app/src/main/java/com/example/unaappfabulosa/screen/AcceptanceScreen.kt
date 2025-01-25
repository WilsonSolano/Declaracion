package com.example.unaappfabulosa.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*

@Composable
fun AcceptanceScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Animación Lottie romántica de fondo
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Url("https://lottie.host/47fc0320-caac-4d63-a86d-f99b9af401c8/fu8Iugy03S.json")
        )
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Me haces muy feliz!\n♥",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}