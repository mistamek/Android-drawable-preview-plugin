package drawables.dom

import org.w3c.dom.Element
import java.awt.image.BufferedImage
import drawables.ItemDrawableInflater

class AdaptiveIconDrawable : Drawable() {

    companion object {
        private const val BACKGROUND = "background"
        private const val FOREGROUND = "foreground"
    }

    private val drawables = Array<Drawable?>(2, { null })

    override fun inflate(element: Element) {
        super.inflate(element)

        element.childNodes?.also {
            for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element) {
                    ItemDrawableInflater.inflate(childNode)?.apply {
                        when (childNode.tagName) {
                            BACKGROUND -> drawables[0] = this
                            FOREGROUND -> drawables[1] = this
                        }
                    }
                }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        for (drawable in drawables) {
            drawable?.draw(image)
        }
    }
}