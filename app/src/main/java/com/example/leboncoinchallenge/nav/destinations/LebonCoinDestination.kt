package com.example.leboncoinchallenge.nav.destinations

interface LebonCoinDestination {
    val route: String
}
object Home: LebonCoinDestination{
    override val route = "Home"
}
object AlbumDetails: LebonCoinDestination{
    override val route = "AlbumDetails"
}