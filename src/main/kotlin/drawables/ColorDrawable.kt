package drawables

import org.w3c.dom.Element
import java.awt.Color

class ColorDrawable : Drawable() {

    companion object {
        private const val COLOR = "android:color"
    }

    private var color: Color? = null

    override fun inflate(element: Element) {
        super.inflate(element)

        element.attributes?.also {
            it.getNamedItem(COLOR)?.run { ParseUtils.parseAttributeAsColor(this, color) }?.also { color = it }
        }
    }
}