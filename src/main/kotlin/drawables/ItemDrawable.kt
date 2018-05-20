package drawables

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
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
            val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(element.getAttribute(DRAWABLE)))
            virtualFile?.run { IconPreviewFactory.psiManager?.findFile(virtualFile) }?.let {
                val iconDrawable = IconDrawable()
                iconDrawable.icon = IconPreviewFactory.createIcon(it)
                drawable = iconDrawable
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