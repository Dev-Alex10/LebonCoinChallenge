package com.example.leboncoinchallenge

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.leboncoinchallenge.nav.graph.LebonCoinNavHost
import com.example.leboncoinchallenge.ui.theme.LebonCoinChallengeTheme
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LebonCoinChallengeApplication @Inject constructor() : Application()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LebonCoinChallengeApp() {
    LebonCoinChallengeTheme {
        val navController = rememberNavController()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = currentDestination?.route ?: stringResource(R.string.leboncoin))
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            content = {
                LebonCoinNavHost(
                    navHostController = navController,
                    modifier = Modifier
                        .padding(it)
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                )
            })
    }
}