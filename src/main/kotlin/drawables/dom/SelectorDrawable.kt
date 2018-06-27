package drawables.dom

import drawables.ItemDrawableInflater
import drawables.forEach
import org.w3c.dom.Element
import java.awt.image.BufferedImage

open class SelectorDrawable : Drawable() {

    companion object {
        private const val ITEM_TAG = "item"
        private const val STATE_ENABLED = "android:state_enabled"
        private const val TRUE = "true"
        private const val DRAWABLE = "android:drawable"
        private const val ID = "android:id"
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
                    if (childNode.getAttribute(STATE_ENABLED) == TRUE) {
                        nodeToUse = childNode
                        break@loop
                    } else if (!childNode.hasAttributes()) {
                        nodeToUse = childNode
                    } else {
                        childNode.attributes?.forEach { node ->
                            val attribute = node.nodeName
                            if (attribute == DRAWABLE || attribute == ID) {
                                nodeToUse = childNode
                            }
                        }
                    }
                }
            }

            nodeToUse?.also {
                drawable = ItemDrawableInflater.getDrawableWithInflate(it)
            }
        }
    }

    override fun draw(image: BufferedImage) {
        drawable?.draw(image)
    }
}