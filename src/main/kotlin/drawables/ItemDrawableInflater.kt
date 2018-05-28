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
            return getDrawable(element)?.apply { inflate(element) }
        }

        fun getDrawable(element: Element): Drawable? {
            if (element.hasAttribute(DRAWABLE)) {
                val drawableAttr = element.getAttribute(DRAWABLE)
                if (drawableAttr.startsWith("#")) {
                    return ColorDrawable(drawableAttr)
                } else {
                    ParseUtils.getPsiFileFromPath(drawableAttr)?.let {
                        return IconPreviewFactory.createDrawable(it)
                                ?: IconDrawable().apply { icon = IconPreviewFactory.createIconInner(it) }
                    }
                }
            } else if (element.hasChildNodes()) {
                val childNodes = element.childNodes
                for (i in 0 until childNodes.length) {
                    val childNode = childNodes.item(i)
                    if (childNode is Element) {
                        return DrawableInflater.getDrawable(childNode)
                    }
                }
            }

            return null
        }
    }
}