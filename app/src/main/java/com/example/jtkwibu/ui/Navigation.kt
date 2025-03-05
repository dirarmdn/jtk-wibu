package com.example.jtkwibu.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmark : Screen("bookmark")
    object Profile : Screen("profile")
    object AnimeDetail : Screen("detail/{malId}") {
        fun createRoute(malId: Int) = "detail/$malId"
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Onboarding.route) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(onAnimeClick = { malId ->
                navController.navigate("detail/$malId")
            })
        }
        composable(Screen.Search.route) {
            SearchScreen(onAnimeClick = {  })
        }
        composable(Screen.Bookmark.route) { BookmarkScreen(onAnimeClick = {  }) }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(Screen.AnimeDetail.route) { backStackEntry ->
            val malId = backStackEntry.arguments?.getString("malId")?.toIntOrNull()
            if (malId != null) {
                AnimeDetailScreen(
                    malId = malId,
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}
