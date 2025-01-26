package com.example.unaappfabulosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unaappfabulosa.screen.AcceptanceScreen
import com.example.unaappfabulosa.screen.ProposalScreen
import com.example.unaappfabulosa.screen.SplashScreen
import com.example.unaappfabulosa.ui.theme.UnaAppFabulosaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnaAppFabulosaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RomanceApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RomanceApp(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable(
            "splash",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ) + fadeOut()
            }
        ) {
            SplashScreen(onNavigateToProposal = {
                navController.navigate("proposal")
            })
        }

        composable(
            "proposal",
            enterTransition = {

                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ) + fadeIn()
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700))
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(500)
                ) + fadeIn()
            }
        ) {
            ProposalScreen(onAccept = {
                navController.navigate("acceptance")
            })
        }

        composable(
            "acceptance",
            enterTransition = {
                scaleIn(
                    initialScale = 5f,
                    animationSpec = tween(800)
                ) + fadeIn()
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(500))
            }
        ) {
            AcceptanceScreen()
        }
    }
}