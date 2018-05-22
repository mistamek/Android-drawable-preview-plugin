package drawables

import com.intellij.openapi.vfs.LocalFileSystem
import drawables.dom.ColorDrawable
import drawables.dom.Drawable
import drawables.dom.IconDrawable
import org.w3c.dom.Element
import java.io.File

class ItemDrawableInflater {

    companion object {
        private const val DRAWABLE = "android:drawable"

        fun inflate(element: Element): Drawable? {
            if (element.hasAttribute(DRAWABLE)) {
                val drawableAttr = element.getAttribute(DRAWABLE)
                if (drawableAttr.startsWith("#")) {
                    return ColorDrawable(drawableAttr)
                } else {
                    val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(drawableAttr))
                    virtualFile?.run { IconPreviewFactory.psiManager?.findFile(this) }?.let {
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