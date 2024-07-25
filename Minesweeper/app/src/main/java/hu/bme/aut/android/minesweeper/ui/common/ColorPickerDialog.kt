package hu.bme.aut.android.minesweeper.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import hu.bme.aut.android.minesweeper.R

@Composable
fun ColorPickerDialg(
    onConfirm: (Color) -> Unit,
    onDismiss: () -> Unit
){
    val controller = rememberColorPickerController()
    var color = Color(0xFFFFFFFF)
    AlertDialog(
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(5.dp),
                    controller = controller,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        color = colorEnvelope.color
                    },
                    initialColor = color
                )
                AlphaSlider(
                    modifier = Modifier
                       .fillMaxWidth()
                      .padding(5.dp)
                     .height(35.dp),
                    controller = controller,
                )
                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .height(35.dp),
                    controller = controller,
                )
                AlphaTile(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    controller = controller
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(color) }) {
                Text(text = stringResource(id = R.string.confirm), color = MaterialTheme.colorScheme.tertiary)
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
