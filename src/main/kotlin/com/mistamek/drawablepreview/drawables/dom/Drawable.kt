package com.mistamek.drawablepreview.drawables.dom

import org.w3c.dom.Element
import java.awt.image.BufferedImage

abstract class Drawable {

    open fun inflate(element: Element) {
    }

    open fun draw(outputImage: BufferedImage) {
    }
}