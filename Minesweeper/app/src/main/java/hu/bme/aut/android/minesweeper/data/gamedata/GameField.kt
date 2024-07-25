package hu.bme.aut.android.minesweeper.data.gamedata

import java.io.Serializable

class GameField: Serializable {
    var value = 0
    var flagged = false
    var open = false
}