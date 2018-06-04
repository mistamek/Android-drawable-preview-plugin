package drawables.dom

import drawables.Utils
import org.w3c.dom.Element
import java.awt.Color
import java.awt.image.BufferedImage

class ColorDrawable() : Drawable() {

    companion object {
        private const val COLOR = "android:color"
    }

    constructor(string: String) : this() {
        this.color = Utils.parseAttributeAsColor(string, null)
    }

    private var color: Color? = null

    override fun inflate(element: Element) {
        super.inflate(element)

        element.getAttribute(COLOR)?.run { Utils.parseAttributeAsColor(this, color) }?.also { color = it }
    }

    override fun draw(image: BufferedImage) {
        val graphics = image.graphics
        graphics.color = color
        graphics.fillRect(0, 0, image.width, image.height)
    }
}