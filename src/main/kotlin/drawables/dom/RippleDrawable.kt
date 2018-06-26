package drawables.dom

import drawables.ItemDrawableInflater
import drawables.Utils
import drawables.forEachAsElement
import org.w3c.dom.Element
import java.awt.Color
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class RippleDrawable : Drawable() {

    companion object {
        private const val COLOR = "android:color"
        private const val ITEM_TAG = "item"
    }

    private var color = Color(0F, 0F, 0F, 0F)
    private var backgroundDrawables = ArrayList<Drawable>()

    override fun inflate(element: Element) {
        super.inflate(element)
        color = Utils.parseAttributeAsColor(element.getAttribute(COLOR), color)?.let {
            Color(it.red, it.green, it.blue, (255 * 0.5F).toInt())
        } ?: color

        element.childNodes.forEachAsElement { childElement ->
            if (childElement.tagName == ITEM_TAG) {
                ItemDrawableInflater.getDrawableWithInflate(childElement)?.also { backgroundDrawables.add(it) }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        backgroundDrawables.forEach { it.draw(image) }

        image.createGraphics().also { graphics ->
            val resolvedSize = (image.width * 0.5F).toInt()

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            graphics.color = color
            graphics.fillOval((image.width / 2) - (resolvedSize / 2), (image.height / 2) - (resolvedSize / 2), resolvedSize, resolvedSize)
            graphics.dispose()
        }
    }
}