package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.drawables.ItemDrawableInflater
import com.mistamek.drawablepreview.drawables.Utils
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class InsetDrawable : Drawable() {

    companion object {
        private const val INSET = "android:inset"
        private const val INSET_TOP = "android:insetTop"
        private const val INSET_LEFT = "android:insetLeft"
        private const val INSET_RIGHT = "android:insetRight"
        private const val INSET_BOTTOM = "android:insetBottom"
    }

    private var drawable: Drawable? = null

    private var insetTop = 0
    private var insetLeft = 0
    private var insetRight = 0
    private var insetBottom = 0

    override fun inflate(element: Element) {
        super.inflate(element)
        drawable = ItemDrawableInflater.getDrawableWithInflate(element)

        Utils.parseAttributeAsInt(element.getAttribute(INSET), 0).also { inset ->
            insetTop = inset
            insetLeft = inset
            insetRight = inset
            insetBottom = inset
        }

        insetTop = Utils.parseAttributeAsInt(element.getAttribute(INSET_TOP), insetTop)
        insetLeft = Utils.parseAttributeAsInt(element.getAttribute(INSET_LEFT), insetLeft)
        insetRight = Utils.parseAttributeAsInt(element.getAttribute(INSET_RIGHT), insetRight)
        insetBottom = Utils.parseAttributeAsInt(element.getAttribute(INSET_BOTTOM), insetBottom)
    }

    override fun draw(outputImage: BufferedImage) {
        super.draw(outputImage)

        drawable?.also { drawable ->
            val maxValueAsFloat = (arrayOf(insetTop, insetLeft, insetRight, insetBottom).max() ?: insetLeft).toFloat()
            val maxInsetSize = outputImage.width / 5
            insetTop = ((insetTop / maxValueAsFloat) * maxInsetSize).toInt()
            insetLeft = ((insetLeft / maxValueAsFloat) * maxInsetSize).toInt()
            insetRight = ((insetRight / maxValueAsFloat) * maxInsetSize).toInt()
            insetBottom = ((insetBottom / maxValueAsFloat) * maxInsetSize).toInt()

            val width = outputImage.width - insetLeft - insetRight
            val height = outputImage.height - insetTop - insetBottom
            if (width <= 0 || height <= 0) {
                return
            }

            BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).also { imageWithInsets ->
                drawable.draw(imageWithInsets)
                outputImage.graphics.apply {
                    drawImage(imageWithInsets, insetLeft, insetTop, width, height, null)
                    dispose()
                }
            }
        }
    }
}