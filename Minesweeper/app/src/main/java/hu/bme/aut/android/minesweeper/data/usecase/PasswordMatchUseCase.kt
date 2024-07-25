package hu.bme.aut.android.minesweeper.data.usecase

class PasswordsMatchUseCase {

    operator fun invoke(password: String, confirmPassword: String): Boolean =
        password == confirmPassword
}