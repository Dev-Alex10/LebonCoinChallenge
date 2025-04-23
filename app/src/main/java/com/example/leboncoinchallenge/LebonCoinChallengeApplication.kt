package com.example.leboncoinchallenge

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.leboncoinchallenge.nav.graph.LebonCoinNavHost
import com.example.leboncoinchallenge.ui.theme.LebonCoinChallengeTheme
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LebonCoinChallengeApplication @Inject constructor() : Application()

@Composable
fun LebonCoinChallengeApp() {
    LebonCoinChallengeTheme {
        val navController = rememberNavController()
        Scaffold {
            LebonCoinNavHost(navHostController = navController, modifier = Modifier.padding(it))
        }
    }
}