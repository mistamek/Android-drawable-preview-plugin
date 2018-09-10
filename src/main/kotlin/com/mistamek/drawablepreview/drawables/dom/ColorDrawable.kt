package com.mistamek.drawablepreview.drawables.dom

import com.mistamek.drawablepreview.drawables.Utils
import org.w3c.dom.Element
import java.awt.Color
import java.awt.image.BufferedImage

class ColorDrawable() : Drawable() {

    companion object {
        private const val COLOR = "android:color"
    }

    constructor(string: String) : this() {
        color = Utils.parseAttributeAsColor(string, null)
    }

    private var color: Color? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        color = Utils.parseAttributeAsColor(element.getAttribute(COLOR), color)
    }

    override fun draw(outputImage: BufferedImage) {
        outputImage.graphics.also { graphics ->
            graphics.color = color
            graphics.fillRect(0, 0, outputImage.width, outputImage.height)
            graphics.dispose()
        }
    }
}