package com.example.unaappfabulosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onNavigateToProposal = {
                navController.navigate("proposal")
            })
        }
        composable("proposal") {
            ProposalScreen(onAccept = {
                navController.navigate("acceptance")
            })
        }
        composable("acceptance") {
            AcceptanceScreen()
        }
    }
}