package drawables.dom

import org.w3c.dom.Element
import java.awt.image.BufferedImage
import drawables.ItemDrawableInflater

class LayerDrawable : Drawable() {

    // TODO Not sure how to implement attributes such as width height,
    // TODO need to come back later

    companion object {
        private const val ITEM_TAG = "item"
    }

    private val drawables = ArrayList<Drawable>()

    override fun inflate(element: Element) {
        super.inflate(element)

        element.childNodes?.also {
            for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element && childNode.tagName?.equals(ITEM_TAG) == true) {
                    ItemDrawableInflater.inflate(childNode)?.apply {
                        drawables.add(this)
                    }
                }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        for (drawable in drawables) {
            drawable.draw(image)
        }
    }
}