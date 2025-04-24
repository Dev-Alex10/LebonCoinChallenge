package com.example.leboncoinchallenge.nav.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leboncoinchallenge.nav.destinations.Home
import com.example.leboncoinchallenge.ui.home.HomeView

@Composable
fun LebonCoinNavHost(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(Home.route) {
            HomeView(modifier = modifier)
        }
    }
}