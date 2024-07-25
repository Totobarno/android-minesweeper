package hu.bme.aut.android.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hu.bme.aut.android.minesweeper.navigation.NavGraph
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperTheme(custom = custom) {
                NavGraph()
            }
        }
    }
}