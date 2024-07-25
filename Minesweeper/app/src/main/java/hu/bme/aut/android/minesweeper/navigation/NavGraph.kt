package hu.bme.aut.android.minesweeper.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.minesweeper.feature.game.GameScreen
import hu.bme.aut.android.minesweeper.feature.game_auth.login.LoginScreen
import hu.bme.aut.android.minesweeper.feature.game_auth.register.RegisterScreen
import hu.bme.aut.android.minesweeper.feature.leaderboard.LeaderboardScreen
import hu.bme.aut.android.minesweeper.feature.menu.difficulty.DifficultyMenuScreen
import hu.bme.aut.android.minesweeper.feature.menu.main_menu.MainMenuScreen
import hu.bme.aut.android.minesweeper.feature.settings.SettingScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {
                        -it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {
                        -it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            }
        ) {
            LoginScreen(
                onSuccess = {
                    navController.navigate(Screen.MainMenu.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(
            route = Screen.Register.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            },
            exitTransition = {
                if(targetState.destination.route == Screen.MainMenu.route){
                    slideOutHorizontally(
                        targetOffsetX = {
                            -it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
                else{
                    slideOutHorizontally(
                        targetOffsetX = {
                            it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
            }
        ){
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Login.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Login.route)
                },
                onSuccess = {
                    navController.navigate(Screen.MainMenu.route)
                }
            )
        }
        composable(
            route = Screen.MainMenu.route,
            enterTransition = {
                if(initialState.destination.route == Screen.Login.route ||
                    initialState.destination.route == Screen.Register.route){
                    slideInHorizontally(
                        initialOffsetX = {
                            it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
                else{
                    slideInHorizontally(
                        initialOffsetX = {
                            -it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
            },
            exitTransition = {
                if(targetState.destination.route == Screen.Login.route){
                    slideOutHorizontally(
                        targetOffsetX = {
                            it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
                else{
                    slideOutHorizontally(
                        targetOffsetX = {
                            -it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
            }
        ){
            MainMenuScreen(
                onNewGameClick = {
                    navController.navigate(Screen.DifficultyMenu.passBool(true))
                },
                onSignOut = {
                    navController.popBackStack(
                        route = Screen.Login.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Login.route)
                },
                onLoadGame = { row, column, mine, diff, load ->
                    navController.navigate(Screen.GameScreen.passField(row, column, mine, diff, load))
                },
                onLeaderboardClick = {
                    navController.navigate(Screen.DifficultyMenu.passBool(false))
                },
                onSettingsClick = {
                    navController.navigate(Screen.SettingScreen.route)
                }
            )
        }
        composable(
            route = Screen.DifficultyMenu.route,
            arguments = listOf(
                navArgument("game"){
                    type = NavType.BoolType
                }
            ),
            enterTransition = {
                if(initialState.destination.route == Screen.MainMenu.route){
                    slideInHorizontally(
                        initialOffsetX = {
                            it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
                else{
                    slideInHorizontally(
                        initialOffsetX = {
                            -it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
            },
            exitTransition = {
                if(targetState.destination.route == Screen.MainMenu.route){
                    slideOutHorizontally(
                        targetOffsetX = {
                            it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
                else{
                    slideOutHorizontally(
                        targetOffsetX = {
                            -it
                        },
                        animationSpec = tween(
                            durationMillis = 1000
                        )
                    )
                }
            }
        ){
            DifficultyMenuScreen(
                onGameButtonClick = { row, column, mine, diff, load ->
                    navController.navigate(Screen.GameScreen.passField(row, column, mine, diff, load))
                },
                onLeaderboardButtonClick = { diff ->
                    navController.navigate(Screen.LeaderboardScreen.passDifficulty(diff))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.GameScreen.route,
            arguments = listOf(
                navArgument("row"){
                    type = NavType.IntType
                },
                navArgument("col"){
                    type = NavType.IntType
                },
                navArgument("mine"){
                    type = NavType.IntType
                },
                navArgument("diff"){
                    type = NavType.IntType
                },
                navArgument("load"){
                    type = NavType.BoolType
                }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            }
        ){
            GameScreen(onNavigateBack = { navController.navigate(Screen.MainMenu.route) })
        }
        composable(
            route = Screen.LeaderboardScreen.route,
            arguments = listOf(
                navArgument("diff"){
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            }
        ){
            LeaderboardScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.SettingScreen.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = {
                        it
                    },
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            }
        ){
            SettingScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
