package drawables

import IconPreviewFactory
import drawables.dom.ColorDrawable
import drawables.dom.Drawable
import drawables.dom.IconDrawable
import org.w3c.dom.Element

class ItemDrawableInflater {

    companion object {
        private const val DRAWABLE = "android:drawable"

        fun getDrawableWithInflate(element: Element): Drawable? {
            return getDrawable(element).run {
                second?.also { it.inflate(first) }
            }
        }

        fun getDrawable(element: Element): Pair<Element, Drawable?> {
            if (element.hasAttribute(DRAWABLE)) {
                return element to getDrawableFromAttribute(element)
            } else if (element.hasChildNodes()) {
                return getDrawableFromChild(element)
            }
            return element to null
        }

        private fun getDrawableFromAttribute(element: Element): Drawable? {
            val drawableAttr = element.getAttribute(DRAWABLE)
            if (drawableAttr.startsWith("#")) {
                return ColorDrawable(drawableAttr)
            } else {
                Utils.getPsiFileFromPath(drawableAttr)?.let {
                    return IconPreviewFactory.createDrawable(it)
                            ?: IconDrawable().apply { icon = IconPreviewFactory.createIconInner(it) }
                }
            }
            return null
        }

        private fun getDrawableFromChild(element: Element): Pair<Element, Drawable?> {
            val childNodes = element.childNodes
            for (i in 0 until childNodes.length) {
                val childNode = childNodes.item(i)
                if (childNode is Element) {
                    return childNode to DrawableInflater.getDrawable(childNode)
                }
            }
            return element to null
        }
    }
}