package hu.bme.aut.android.minesweeper

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.bme.aut.android.minesweeper.data.auth.FirebaseAuthService
import hu.bme.aut.android.minesweeper.data.firebase.FirebaseLeaderboardService
import hu.bme.aut.android.minesweeper.data.gamedata.leaderboard.LeaderboardService

class MinesweeperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        authService = FirebaseAuthService(FirebaseAuth.getInstance())
        leaderboardService = FirebaseLeaderboardService(FirebaseFirestore.getInstance(), authService)
    }

    companion object{
        lateinit var authService : FirebaseAuthService
        lateinit var leaderboardService: LeaderboardService
    }
}