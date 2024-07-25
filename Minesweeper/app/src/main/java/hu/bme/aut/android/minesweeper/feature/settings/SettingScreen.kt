package hu.bme.aut.android.minesweeper.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.minesweeper.R
import hu.bme.aut.android.minesweeper.ui.common.ColorPickerDialg
import hu.bme.aut.android.minesweeper.ui.theme.MinesweeperTheme
import hu.bme.aut.android.minesweeper.ui.theme.custom

@ExperimentalMaterial3Api
@Composable
fun SettingScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingViewModel = viewModel(factory = SettingViewModel.Factory)
){
    var showPrimaryDialog by remember{ mutableStateOf(false) }
    var showSecondaryDialog by remember{ mutableStateOf(false) }
    var showTertiaryDialog by remember{ mutableStateOf(false) }
    var change by remember{ mutableStateOf(false) }
    MinesweeperTheme(custom = custom, change = change) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.settings)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.tertiary,
                        actionIconContentColor = MaterialTheme.colorScheme.tertiary,
                        navigationIconContentColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Button(onClick = {
                        showPrimaryDialog = true
                    }) {
                        Text(text = stringResource(id = R.string.button_color), color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = {
                        showSecondaryDialog = true
                    }) {
                        Text(text = stringResource(id = R.string.background_color), color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = {
                        showTertiaryDialog = true
                    }) {
                        Text(text = stringResource(id = R.string.text_color), color = MaterialTheme.colorScheme.tertiary)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = {
                        viewModel.defaultColor()
                        change = !change
                    }) {
                        Text(text = stringResource(id = R.string.default_color), color = MaterialTheme.colorScheme.tertiary)
                    }
                }
                if(showPrimaryDialog){
                    ColorPickerDialg(
                        onConfirm = {color ->
                            viewModel.ChangePrimaryColor(color)
                            change = !change
                            showPrimaryDialog = false
                        },
                        onDismiss = { showPrimaryDialog = false }
                    )
                }
                if(showSecondaryDialog){
                    ColorPickerDialg(
                        onConfirm = {color ->
                            viewModel.ChangeSecondaryColor(color)
                            change = !change
                            showSecondaryDialog = false
                        },
                        onDismiss = { showSecondaryDialog = false }
                    )
                }
                if(showTertiaryDialog){
                    ColorPickerDialg(
                        onConfirm = {color ->
                            viewModel.ChangeTertiaryColor(color)
                            change = !change
                            showTertiaryDialog = false
                        },
                        onDismiss = { showTertiaryDialog = false }
                    )
                }
            }
        }
    }
}