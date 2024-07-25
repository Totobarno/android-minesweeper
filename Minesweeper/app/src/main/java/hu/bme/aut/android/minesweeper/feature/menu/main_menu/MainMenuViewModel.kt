package hu.bme.aut.android.minesweeper.feature.menu.main_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.minesweeper.MinesweeperApplication
import hu.bme.aut.android.minesweeper.data.auth.AuthService
import kotlinx.coroutines.launch

class MainMenuViewModel (
    private val authService: AuthService
): ViewModel(){

    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val auth = MinesweeperApplication.authService
                MainMenuViewModel(
                    authService = auth
                )
            }
        }
    }
}