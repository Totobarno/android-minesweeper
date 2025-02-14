package hu.bme.aut.android.minesweeper.util

import hu.bme.aut.android.minesweeper.ui.model.UiText

sealed class UiEvent {
    object Success: UiEvent()

    data class Failure(val message: UiText): UiEvent()
}