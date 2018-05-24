package drawables.dom

import drawables.ItemDrawableInflater
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class LevelListDrawable : Drawable() {

    companion object {
        private const val ITEM_TAG = "item"
    }

    private var drawable: Drawable? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        element.childNodes?.also {
            var nodeToUse: Element? = null

            loop@ for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element && childNode.tagName?.equals(ITEM_TAG) == true) {
                    nodeToUse = childNode
                    break@loop
                }
            }

            nodeToUse?.let {
                drawable = ItemDrawableInflater.inflate(it)
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        drawable?.draw(image)
    }
}