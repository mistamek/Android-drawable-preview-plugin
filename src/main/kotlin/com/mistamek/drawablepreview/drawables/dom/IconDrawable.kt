package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.drawables.Utils
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon

class IconDrawable : Drawable() {

    var icon: Icon? = null

    override fun draw(image: BufferedImage) {
        super.draw(image)
        icon?.also { icon ->
            if (icon is ImageIcon) {
                Utils.drawResizedIcon(icon.image, image)
            }
        }
    }
}