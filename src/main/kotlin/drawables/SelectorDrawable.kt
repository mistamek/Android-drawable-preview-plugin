package drawables

import org.w3c.dom.Element
import java.awt.image.BufferedImage

open class SelectorDrawable : Drawable() {

    // TODO maybe add more logic

    companion object {
        private const val ITEM_TAG = "item"
    }

    private val childDrawables = ArrayList<Drawable>()

    override fun inflate(element: Element) {
        super.inflate(element)
        inflateChildDrawables(element)
    }

    private fun inflateChildDrawables(element: Element) {
        element.childNodes?.let {
            for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element && childNode.tagName?.equals(ITEM_TAG) == true) {
                    DrawableInflater.getDrawable(childNode)?.let {
                        childDrawables.add(it)
                    }
                }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        childDrawables.firstOrNull()?.draw(image)
    }
}