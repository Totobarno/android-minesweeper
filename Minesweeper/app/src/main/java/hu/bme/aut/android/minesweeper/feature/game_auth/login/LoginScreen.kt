package hu.bme.aut.android.minesweeper.feature.game_auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.minesweeper.R
import hu.bme.aut.android.minesweeper.util.UiEvent
import hu.bme.aut.android.minesweeper.ui.common.EmailTextField
import hu.bme.aut.android.minesweeper.ui.common.PasswordTextField
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    onSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginUserViewModel = viewModel(factory = LoginUserViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = SnackbarHostState()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Success -> {
                    onSuccess()
                }
                is UiEvent.Failure -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message.asString(context)
                        )
                    }
                }
            }
        }
    }

    MinesweeperTheme(custom = custom) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.secondary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(
                    value = state.email,
                    label = stringResource(R.string.textfield_label_email),
                    onValueChange = { viewModel.onEvent(LoginUserEvent.EmailChanged(it)) },
                    onDone = {},
                    imeAction = ImeAction.Next,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                PasswordTextField(
                    value = state.password,
                    label = stringResource(R.string.textfield_label_password),
                    onValueChange = { viewModel.onEvent(LoginUserEvent.PasswordChanged(it)) },
                    onDone = {},
                    modifier = Modifier.padding(bottom = 10.dp),
                    isVisible = state.passwordVisibility,
                    onVisibilityChanged = { viewModel.onEvent(LoginUserEvent.PasswordVisibilityChanged) }
                )
                Button(
                    onClick = { viewModel.onEvent(LoginUserEvent.SignIn) },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(text = stringResource(R.string.button_text_sign_in), color = MaterialTheme.colorScheme.tertiary)
                }
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(text = stringResource(R.string.button_text_no_account), color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}