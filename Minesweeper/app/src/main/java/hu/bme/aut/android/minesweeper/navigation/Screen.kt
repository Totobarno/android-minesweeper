package hu.bme.aut.android.minesweeper.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register: Screen("register")
    object MainMenu : Screen("main_menu")
    object DifficultyMenu : Screen("difficulty_menu/{game}"){
        fun passBool(game: Boolean) = "difficulty_menu/$game"
    }
    object GameScreen : Screen("game_screen/{row}/{col}/{mine}/{diff}/{load}"){
        fun passField(row: Int, col: Int, mine: Int, diff: Int, load: Boolean) = "game_screen/$row/$col/$mine/$diff/$load"
    }
    object LeaderboardScreen : Screen("leaderboard_screen/{diff}"){
        fun passDifficulty(diff: Int) = "leaderboard_screen/$diff"
    }
    object SettingScreen : Screen("setting")
}