package hu.bme.aut.android.minesweeper.feature.settings

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.minesweeper.ui.theme.CustomColorScheme
import hu.bme.aut.android.minesweeper.ui.theme.custom
import hu.bme.aut.android.minesweeper.ui.theme.customButton
import hu.bme.aut.android.minesweeper.ui.theme.customBackground
import hu.bme.aut.android.minesweeper.ui.theme.customText

class SettingViewModel : ViewModel() {
    fun ChangePrimaryColor(color: Color){
        customButton = color
        CustomColorScheme = lightColorScheme(
            primary = customButton,
            secondary = customBackground,
            tertiary = customText
        )
        custom = true
    }
    fun ChangeSecondaryColor(color: Color){
        customBackground = color
        CustomColorScheme = lightColorScheme(
            primary = customButton,
            secondary = customBackground,
            tertiary = customText
        )
        custom = true
    }
    fun ChangeTertiaryColor(color: Color){
        customText = color
        CustomColorScheme = lightColorScheme(
            primary = customButton,
            secondary = customBackground,
            tertiary = customText
        )
        custom = true
    }
    fun defaultColor(){
        custom = false
        customButton = Color(0xFFD0BCFF)
        customBackground = Color(0xFFF5F5F5)
        customText = Color(0xFF000000)
    }
    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingViewModel()
            }
        }
    }
}