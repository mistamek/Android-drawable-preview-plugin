package com.mistamek.drawablepreview.factories

import com.mistamek.drawablepreview.drawables.Utils
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object BitmapImageFactory {
    fun createBitmapImage(path: String): BufferedImage? {
        ImageIO.read(File(path)).let {
            val output = BufferedImage(Constants.ICON_SIZE, Constants.ICON_SIZE, BufferedImage.TYPE_INT_ARGB)
            Utils.drawResizedIcon(it, output)
            return output
        }
    }
}