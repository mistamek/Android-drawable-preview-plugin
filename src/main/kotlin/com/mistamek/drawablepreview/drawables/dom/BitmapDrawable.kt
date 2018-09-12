package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.factories.IconPreviewFactory
import com.mistamek.drawablepreview.drawables.Utils
import org.w3c.dom.Element
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.image.BufferedImage

class BitmapDrawable : Drawable() {

    companion object {
        private const val SRC = "android:src"
        private const val TINT = "android:tint"
    }

    private var childImage: BufferedImage? = null
    private var tintColor: Color? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        childImage = element.getAttribute(SRC)
                ?.let { Utils.getPsiFileFromPath(it) }
                ?.run { IconPreviewFactory.getImage(this) }

        tintColor = Utils.parseAttributeAsColor(element.getAttribute(TINT), tintColor)
    }

    override fun draw(outputImage: BufferedImage) {
        super.draw(outputImage)
        childImage?.also { childImage ->
            val width = childImage.width
            val height = childImage.height
            BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).also { dyed ->

                dyed.createGraphics().also { graphics ->
                    graphics.drawImage(childImage, 0, 0, null)
                    tintColor?.also {
                        graphics.composite = AlphaComposite.SrcAtop
                        graphics.color = tintColor
                        graphics.fillRect(0, 0, width, height)
                    }
                    graphics.dispose()
                }

                Utils.drawResizedIcon(dyed, outputImage)
            }
        }
    }
}