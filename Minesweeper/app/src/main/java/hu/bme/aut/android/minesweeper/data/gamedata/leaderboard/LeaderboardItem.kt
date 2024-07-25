package hu.bme.aut.android.minesweeper.data.gamedata.leaderboard

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime


data class LeaderboardItem (
    val id: String = "",
    val score: Int = 0,
    val name: String = "",
    val date: LocalDate = LocalDateTime.now().toKotlinLocalDateTime().date,
    val difficulty : Int = 0
)