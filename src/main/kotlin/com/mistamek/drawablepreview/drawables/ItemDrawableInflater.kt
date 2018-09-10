package com.mistamek.drawablepreview.drawables

import com.mistamek.drawablepreview.IconPreviewFactory
import com.mistamek.drawablepreview.drawables.dom.ColorDrawable
import com.mistamek.drawablepreview.drawables.dom.Drawable
import com.mistamek.drawablepreview.drawables.dom.IconDrawable
import org.w3c.dom.Element

object ItemDrawableInflater {
    private const val DRAWABLE = "android:drawable"

    fun getDrawableWithInflate(element: Element): Drawable? {
        return getDrawable(element).let {
            val drawable = it.second
            val elementToUse = it.first
            if (elementToUse != null) {
                drawable?.inflate(elementToUse)
            }
            drawable
        }
    }

    fun getDrawable(element: Element): Pair<Element?, Drawable?> {
        if (element.hasAttribute(DRAWABLE)) {
            return null to getDrawableFromAttribute(element)
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
                        ?: IconDrawable().apply { childImage = IconPreviewFactory.getImage(it) }
            }
        }
        return null
    }

    private fun getDrawableFromChild(element: Element): Pair<Element, Drawable?> {
        element.childNodes?.forEachAsElement { childNode ->
            return childNode to DrawableInflater.getDrawable(childNode)
        }
        return element to null
    }
}