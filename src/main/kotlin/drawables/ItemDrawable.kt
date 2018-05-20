package drawables

import DrawableInflater
import IconPreviewFactory
import com.intellij.openapi.vfs.LocalFileSystem
import org.w3c.dom.Element
import java.awt.image.BufferedImage
import java.io.File

class ItemDrawable : Drawable() {

    companion object {
        private const val DRAWABLE = "android:drawable"
    }

    private var drawable: Drawable? = null

    override fun inflate(element: Element) {
        super.inflate(element)
        if (element.hasAttribute(DRAWABLE)) {
            val drawableAttr = element.getAttribute(DRAWABLE)
            if (drawableAttr.startsWith("#")) {
                drawable = ColorDrawable(drawableAttr)
            } else {
                val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(drawableAttr))
                virtualFile?.run { IconPreviewFactory.psiManager?.findFile(virtualFile) }?.let {
                    val iconDrawable = IconDrawable()
                    iconDrawable.icon = IconPreviewFactory.createIcon(it)
                    drawable = iconDrawable
                }
            }
        } else if (element.hasChildNodes()) {
            val childNodes = element.childNodes
            for (i in 0 until childNodes.length) {
                val childNode = childNodes.item(i)
                if (childNode is Element) {
                    drawable = DrawableInflater.getDrawable(childNode)
                }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        drawable?.draw(image)
    }
}