package ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.timerTask

class AlertBox {
    var text: String = ""
    var displayed by mutableStateOf(false)

    @Composable
    fun CreateAlert(
        screenWidth: Dp,
        screenHeight: Dp,
        themeColor: List<Color>,
        cardGrad1: Int = 8,
        cardGrad2: Int = 9,
        borderWidth: Dp = 1.dp,
        borderColor: Int = 18,
        iconColor: Int = 4,
        textColor: Int = 2
    ) {
        verticalVisibilityPane(
            visibility = displayed, animationHeight = 2, duration = 250, paneContent = {
                createCard(
                    xOffset = 5.dp, yOffset = screenHeight-60.dp,
                    width = screenWidth-65.dp, height = 50.dp, elevation = 20.dp,
                    themeColor = themeColor, cardGrad1 = cardGrad1, cardGrad2 = cardGrad2,
                    borderWidth = borderWidth, borderColor = borderColor,
                    cardContent = {
                        textRow(
                            displayedText = text, textOffset = 15.dp,
                            fontSize = 18.sp, font = FontWeight.Normal, themeColor = themeColor, textColor = textColor
                        )
                    }
                )
                createCard(
                    xOffset = screenWidth-55.dp, yOffset = screenHeight-60.dp,
                    width = 50.dp, height = 50.dp, elevation = 20.dp,
                    themeColor = themeColor, cardGrad1 = cardGrad1, cardGrad2 = cardGrad2,
                    borderWidth = borderWidth, borderColor = borderColor,
                    cardContent = {
                        IconButton(onClick = { displayed = false }) {
                            Icon(
                                imageVector = Icons.Sharp.Close,
                                contentDescription = "Close Button",
                                tint = themeColor[iconColor]
                            )
                        }
                    }
                )
            }
        )
    }

    fun DisplayAlert(
        displayedText: String,
        displayTime: Long = 3000
    ) {
        Thread(Runnable {
            runBlocking {
                WaitForCondition(50000, 100)
            }
            this.text = displayedText
            this.displayed = true
            Timer().schedule(timerTask { displayed = false }, displayTime)
        }).start()
    }

    tailrec suspend fun WaitForCondition(maxDelay: Long, checkPeriod: Long) : Boolean{
        if(maxDelay < 0) return false
        if(!displayed) return true
        delay(checkPeriod)
        return WaitForCondition(maxDelay - checkPeriod, checkPeriod)
    }
}