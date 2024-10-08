package ui

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ui.ViewModel.displayedImage
import ui.ViewModel.imageBitmapPainter
import ui.ViewModel.imageDisplay
import ui.ViewModel.imageInputStream
import ui.ViewModel.imageModifier
import ui.ViewModel.screenHeight
import ui.ViewModel.screenWidth
import ui.ViewModel.themeColor
import utils.storage.*
import java.awt.image.BufferedImage

/**
 * ViewModel implementation for the application
 * Holds shared variables and passes them through scopes
 *
 * @property themeColor The currently selected theme for the application
 * @property displayedImage The currently displayed image
 * @property displayedNodes The graphics for the generated nodes
 * @property imageInputStream An input stream for writing to the [displayedImage]
 * @property nodeInputStream An input stream for writing to the [displayedNodes]
 * @property imageBitmapPainter A bitmap painter for updating the graphics of the [displayedImage]
 * @property nodeBitmapPainter A bitmap painter for updating the graphics of the [displayedNodes]
 * @property MenuPage The current page of the main menu
 * @property screenWidth The width of the target screen
 * @property screenHeight The height of the target screen
 * @property imageModifier Controls the modifiers of the displayed image and nodes
 * @property imageDisplay If the image is displayed
 * @property nodeDisplay If the nodes are displayed
 */
object ViewModel {
    // Application theme
    var themeColor by mutableStateOf(ThemeStorage(ThemeData("")))

    // Loaded Themes
    var loadedThemes: ArrayList<ThemeButton> by mutableStateOf(arrayListOf())

    // Image Filters
    var filters: ArrayList<Filter> by mutableStateOf(
        arrayListOf(
            Slice("balls1", 1, 1),
            Slice("poopy2", 2, 2),
            Slice("Squawk3", 3, 3),
            Slice("meow4", 4, 4),
            Slice("woof5", 5, 5)
        )
    )

    // Image Displays
    var displayedImage by mutableStateOf<BufferedImage?>(null)
    var imageInputStream by mutableStateOf<ImageBitmap?>(null)
    var imageBitmapPainter by mutableStateOf<Painter?>(null)

    // Internal variables
    var screenWidth by mutableStateOf(1200.dp)
    var screenHeight by mutableStateOf(800.dp)

    var xScale by mutableStateOf(1.0F)
    var yScale by mutableStateOf(1.0F)

    var imageModifier by mutableStateOf(
        Modifier.size(width = (screenWidth / 2) - 10.dp, height = screenHeight - 230.dp).offset(5.dp, 5.dp)
    )

    // Display Settings
    var imageDisplay by mutableStateOf(false)
}