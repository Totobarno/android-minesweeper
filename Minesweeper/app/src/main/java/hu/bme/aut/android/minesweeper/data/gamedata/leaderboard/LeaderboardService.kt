package hu.bme.aut.android.minesweeper.data.gamedata.leaderboard

import kotlinx.coroutines.flow.Flow

interface LeaderboardService {
    val items : Flow<List<LeaderboardItem>>

    suspend fun saveItem(item: LeaderboardItem)
}