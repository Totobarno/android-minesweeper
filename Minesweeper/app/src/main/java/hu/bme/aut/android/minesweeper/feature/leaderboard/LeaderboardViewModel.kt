package hu.bme.aut.android.minesweeper.feature.leaderboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.minesweeper.MinesweeperApplication
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardItem
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val leaderboardService: LeaderboardService
) : ViewModel() {
    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()
    private lateinit var workList : List<LeaderboardItem>
    private var diff = checkNotNull<Int>(savedStateHandle["diff"])

    init{
        loadLeaderboard()
    }

    private fun loadLeaderboard(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, difficulty = diff) }
            try{
                leaderboardService.items.collect{
                    workList = it.sortedByDescending { it.score }
                    var itemList = mutableListOf<LeaderboardItem>()
                    for(items in workList){
                        if(items.difficulty == diff){
                            itemList.add(items)
                        }
                    }
                    _state.update { it.copy(isLoading = false, items = itemList) }
                }
            }catch (e: Exception){
                _state.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun sortByScore(){
        viewModelScope.launch {
            workList = workList.sortedByDescending { it.score }
            var itemList = mutableListOf<LeaderboardItem>()
            for (items in workList) {
                if (items.difficulty == diff) {
                    itemList.add(items)
                }
            }
            _state.update { it.copy(items = itemList) }
        }
    }

    fun sortByName(){
        viewModelScope.launch {
            workList = workList.sortedBy { it.name }
            var itemList = mutableListOf<LeaderboardItem>()
            for (items in workList) {
                if (items.difficulty == diff) {
                    itemList.add(items)
                }
            }
            _state.update { it.copy(items = itemList) }
        }
    }

    fun sortByDate(){
        viewModelScope.launch {
            workList = workList.sortedBy { it.date }
            var itemList = mutableListOf<LeaderboardItem>()
            for (items in workList) {
                if (items.difficulty == diff) {
                    itemList.add(items)
                }
            }
            _state.update { it.copy(items = itemList) }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val saveStateHandle = createSavedStateHandle()
                val leaderboardService = MinesweeperApplication.leaderboardService
                LeaderboardViewModel(
                    savedStateHandle = saveStateHandle,
                    leaderboardService = leaderboardService
                )
            }
        }
    }
}
data class LeaderboardState(
    val isLoading : Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val items: List<LeaderboardItem> = emptyList(),
    val difficulty: Int = 0
)