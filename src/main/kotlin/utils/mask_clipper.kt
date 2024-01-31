package utils

import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

// TODO: Figure out better input method (needs to return as one or smth)

/**
 * maskToImage
 *
 * Converts a given image to smaller image based on a given mask
 *
 * @param [input] The given image to be cut.
 * @param [mask] The mask to be applied to the given image.
 * @param [name] Output file name.
 */
fun maskToImage(input: BufferedImage, mask: Mask, name: String) {
    // Create Output
    val size = Dimension(mask.bits[1].size, mask.bits.size)
    val img = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB)

    for (x in 0 until size.width) {
        for (y in 0 until size.height) {
            if (mask.bits[y][x] == 1.toByte()) {
                img.setRGB(x, y, input.getRGB(x+mask.position.first, y+mask.position.second))
            }
        }
    }

    ImageIO.write(img, "PNG", File("$name.png"))
}
