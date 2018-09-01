package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.drawables.ItemDrawableInflater
import com.mistamek.drawablepreview.drawables.forEachAsElement
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class LevelListDrawable : Drawable() {

    companion object {
        private const val ITEM_TAG = "item"
    }

    private var drawable: Drawable? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        element.childNodes?.forEachAsElement { childElement ->
            if (childElement.tagName == ITEM_TAG) {
                drawable = ItemDrawableInflater.getDrawableWithInflate(childElement)
                return
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        drawable?.draw(image)
    }
}