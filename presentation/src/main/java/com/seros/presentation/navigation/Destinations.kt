package com.seros.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    data object Splash : Destinations()
    @Serializable
    data object Login : Destinations()
    @Serializable
    data object Register : Destinations()
    @Serializable
    data object Main : Destinations()
    @Serializable
    data object RecoverPassword : Destinations()
}
