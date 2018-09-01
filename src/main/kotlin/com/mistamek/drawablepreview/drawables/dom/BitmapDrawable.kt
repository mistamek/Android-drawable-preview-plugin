package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.IconPreviewFactory
import com.mistamek.drawablepreview.drawables.Utils
import org.w3c.dom.Element
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon


class BitmapDrawable : Drawable() {

    companion object {
        private const val SRC = "android:src"
        private const val TINT = "android:tint"
    }

    private var icon: Icon? = null
    private var tintColor: Color? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        icon = element.getAttribute(SRC)
                ?.let { Utils.getPsiFileFromPath(it) }
                ?.run { IconPreviewFactory.createIconInner(this) }

        tintColor = Utils.parseAttributeAsColor(element.getAttribute(TINT), tintColor)
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        icon?.also { icon ->
            if (icon is ImageIcon) {
                val width = icon.image.getWidth(null)
                val height = icon.image.getHeight(null)

                BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).also { dyed ->

                    dyed.createGraphics().also { graphics ->
                        graphics.drawImage(icon.image, 0, 0, null)
                        tintColor?.also {
                            graphics.composite = AlphaComposite.SrcAtop
                            graphics.color = tintColor
                            graphics.fillRect(0, 0, width, height)
                        }
                        graphics.dispose()
                    }

                    Utils.drawResizedIcon(dyed, image)
                }
            }
        }
    }
}