package drawables.dom

import drawables.ItemDrawableInflater
import drawables.Utils
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
    private var backgroundDrawable: Drawable? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        element.getAttribute(COLOR)?.run { Utils.parseAttributeAsColor(this, color) }
                ?.also { color = Color(it.red, it.green, it.blue, (255 * 0.5F).toInt()) }
        element.childNodes?.also {
            for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element && childNode.tagName?.equals(ITEM_TAG) == true) {
                    backgroundDrawable = ItemDrawableInflater.getDrawableWithInflate(childNode)
                    break
                }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)

        backgroundDrawable?.draw(image)
        image.createGraphics().also {
            val resolvedSize = (image.width * 0.5F).toInt()

            it.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            it.color = color
            it.fillOval((image.width / 2) - (resolvedSize / 2), (image.height / 2) - (resolvedSize / 2), resolvedSize, resolvedSize)
            it.dispose()
        }
    }
}