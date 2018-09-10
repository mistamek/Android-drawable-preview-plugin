package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.drawables.ItemDrawableInflater
import com.mistamek.drawablepreview.drawables.forEachAsElement
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class AdaptiveIconDrawable : Drawable() {

    companion object {
        private const val BACKGROUND = "background"
        private const val FOREGROUND = "foreground"
    }

    private val drawables = Array<Drawable?>(2, { null })

    override fun inflate(element: Element) {
        super.inflate(element)

        element.childNodes?.forEachAsElement { childNode ->
            ItemDrawableInflater.getDrawableWithInflate(childNode)?.apply {
                when (childNode.tagName) {
                    BACKGROUND -> drawables[0] = this
                    FOREGROUND -> drawables[1] = this
                }
            }
        }
    }

    override fun draw(outputImage: BufferedImage) {
        super.draw(outputImage)
        drawables.forEach { it?.draw(outputImage) }
    }
}