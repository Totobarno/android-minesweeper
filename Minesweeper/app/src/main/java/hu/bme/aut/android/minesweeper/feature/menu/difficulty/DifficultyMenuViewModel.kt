package hu.bme.aut.android.minesweeper.feature.menu.difficulty

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DifficultyMenuState{
    object Loading : DifficultyMenuState()
    data class Menu(val isGame: Boolean) : DifficultyMenuState()
}

class DifficultyMenuViewModel(
    private val savedStateHandle: SavedStateHandle
) :ViewModel(){
    private val _state = MutableStateFlow<DifficultyMenuState>(DifficultyMenuState.Loading)
    val state = _state.asStateFlow()

    init{
        viewModelScope.launch {
            val game = checkNotNull<Boolean>(savedStateHandle["game"])
            _state.value = DifficultyMenuState.Menu(
                isGame = game
            )
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val saveStateHandle = createSavedStateHandle()
                DifficultyMenuViewModel(saveStateHandle)
            }
        }
    }
}