/**
 * @author Alexander Yauchler
 * @author Aidan Mao
 *
 * Project Timeline : 1/17/2024 -> TBD (7/18/2024)
 *
 * This software is licensed under the MIT License
 *
 * Written by Aidan Mao and Alexander Yauchler
 * Freshmen in Computer Science at Purdue University Fort Wayne
 *
 * Project direction by Dr. Chen
 */

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.App
import utils.app.getConfigData
import utils.app.getThemes

fun main() = application {
    val config = getConfigData()
    val themes = getThemes()

    val state = rememberWindowState(
        size = DpSize(1200.dp, 800.dp),
        position = WindowPosition(150.dp, 150.dp)
    )

    Window(title = "Image Crunch v0.1.a", onCloseRequest = ::exitApplication, state = state) {
        App()
    }
}