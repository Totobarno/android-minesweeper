package hu.bme.aut.android.minesweeper.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import hu.bme.aut.android.minesweeper.R

@Composable
fun EndGameDialog(
    gameStatus: Boolean,
    score: Int,
    onConfirm: (String) -> Unit,
    onShare: (String) -> Unit,
    onDismiss: () -> Unit,
){
    var nameText by remember{ mutableStateOf("") }
    AlertDialog(
        title = {
            if(gameStatus){
                Text(text = stringResource(id = R.string.you_won), color = MaterialTheme.colorScheme.tertiary)
            }
            else{
                Text(text = stringResource(id = R.string.you_lost), color = MaterialTheme.colorScheme.tertiary)
            }
        },
        text = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "Your score: $score", color = MaterialTheme.colorScheme.tertiary)
                TextField(
                    value = nameText,
                    onValueChange = {nameText = it}
                )
                Button(onClick = { onShare(nameText) }) {
                    Text(text = stringResource(id = R.string.share), color = MaterialTheme.colorScheme.tertiary)
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(nameText) }) {
                Text(text = stringResource(id = R.string.save_score), color = MaterialTheme.colorScheme.tertiary)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.dismiss), color = MaterialTheme.colorScheme.tertiary)
            }
        },
        onDismissRequest = onDismiss
    )
}
