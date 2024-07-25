package hu.bme.aut.android.minesweeper.feature.game

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.minesweeper.MinesweeperApplication
import hu.bme.aut.android.minesweeper.data.gamedata.GameTable
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardItem
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

sealed class GameState{
    object Loading : GameState()
    data class Game(val load: Boolean):GameState()
    data class End(val win: Boolean) : GameState()
}

class GameViewModel(
    private val saveStateHandle: SavedStateHandle,
    private val leaderboardService: LeaderboardService
) : ViewModel() {
    private val _state = MutableStateFlow<GameState>(GameState.Loading)
    val state = _state.asStateFlow()
    private lateinit var table: GameTable
    private var difficulty: Int = 0
    var gameScore: Int = 0
    val playTable: GameTable
        get() = table

    init{
        buildLevel()
    }

    private fun buildLevel(){
        viewModelScope.launch {
                val row = checkNotNull<Int>(saveStateHandle["row"])
                val column = checkNotNull<Int>(saveStateHandle["col"])
                val mine = checkNotNull<Int>(saveStateHandle["mine"])
                difficulty = checkNotNull(saveStateHandle["diff"])
                val loadGame = checkNotNull<Boolean>(saveStateHandle["load"])
                table = GameTable(row, column, mine)
            _state.value = GameState.Game(
                load = loadGame
            )
        }
    }

    fun openCell(row: Int, column: Int){
        viewModelScope.launch {
                table.Table[row][column].open = true
                if (table.Table[row][column].value == 0)
                    openAllEmptyCells(row, column)
                else if(table.Table[row][column].value == 9)
                    checkEndgame(true)
        }
    }

    fun changeFlagState(row: Int, column: Int){
        viewModelScope.launch {
            if(table.Table[row][column].flagged) {
                table.Table[row][column].flagged = false
                table.remaining++
                if(table.remaining == 0)
                    if(table.checkAllFlag())
                        checkEndgame(false)
            }
            else{
                table.Table[row][column].flagged = true
                table.remaining--
                if(table.remaining == 0)
                    if(table.checkAllFlag())
                        checkEndgame(false)
            }
        }
    }

    private fun openAllEmptyCells(row: Int, column: Int){
        viewModelScope.launch {
            table.openEmpty(row, column)
        }
    }

    private fun checkEndgame(explosion: Boolean){
        viewModelScope.launch {
            val score = table.countScore()
            if(explosion) {
                gameScore = score
                _state.value = GameState.End(
                    win = false
                )
            }
            else {
                gameScore = score
                _state.value = GameState.End(
                    win = true
                )
            }
        }
    }

    fun saveScore(name: String){
        viewModelScope.launch {
            leaderboardService.saveItem(
                LeaderboardItem(
                    score = gameScore,
                    name = name,
                    date = LocalDateTime.now().toKotlinLocalDateTime().date,
                    difficulty = difficulty
                )
            )
        }
    }

    fun shareScore(name: String, context: Context){
        val date = LocalDateTime.now().toKotlinLocalDateTime().date
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Minesweeper score: $gameScore" + "\n" +
                        "Player name: $name" + "\n" +
                        "Date: $date")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    fun saveLevel(context: Context){
        viewModelScope.launch {
            table.saveTable("save_game.txt", context)
        }
    }

    fun loadLevel(context: Context){
        viewModelScope.launch {
            table = table.loadTable("save_game.txt", context)
            if(table.Size == 9*9) {
                difficulty = 1
            }else if(table.Size == 16*16){
                difficulty = 2
            }else{
                difficulty = 3
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val saveStateHandle = createSavedStateHandle()
                val leaderboard = MinesweeperApplication.leaderboardService
                GameViewModel(
                    saveStateHandle,
                    leaderboard
                )
            }
        }
    }
}