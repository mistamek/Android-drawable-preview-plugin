package drawables

import org.w3c.dom.Element
import java.awt.image.BufferedImage

open class SelectorDrawable : Drawable() {

    companion object {
        private const val ITEM_TAG = "item"
        private const val STATE_ENABLED = "android:state_enabled"
        private const val DRAWABLE = "android:drawable"
    }

    private var drawable: Drawable? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        inflateChildDrawables(element)
    }

    private fun inflateChildDrawables(element: Element) {
        element.childNodes?.also {
            var nodeToUse: Element? = null

            loop@ for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element && childNode.tagName?.equals(ITEM_TAG) == true) {
                    if (childNode.hasAttribute(STATE_ENABLED)) {
                        nodeToUse = childNode
                        break@loop
                    } else if (!childNode.hasAttributes() || (childNode.attributes.length == 1 && childNode.hasAttribute(DRAWABLE))) {
                        nodeToUse = childNode
                    }
                }
            }

            nodeToUse?.let {
                drawable = DrawableInflater.getDrawable(nodeToUse)
            }
        }
    }

    override fun draw(image: BufferedImage) {
        drawable?.draw(image)
    }
}