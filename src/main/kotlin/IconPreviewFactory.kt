import com.android.ide.common.resources.ResourceResolver
import com.android.resources.ResourceUrl
import com.android.tools.adtui.ImageUtils
import com.android.tools.idea.configurations.ConfigurationManager
import com.android.tools.idea.rendering.GutterIconFactory
import com.android.tools.idea.res.ResourceHelper
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.util.ui.UIUtil
import drawables.DrawableInflater
import drawables.dom.Drawable
import drawables.forEach
import org.w3c.dom.Node
import java.awt.Component
import java.awt.Graphics
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.lang.UnsupportedOperationException
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.xml.parsers.DocumentBuilderFactory


class IconPreviewFactory {

    companion object {
        private const val XML_TYPE = ".xml"
        private const val DRAWABLES_FOLDER_TYPE = "drawable"
        private const val ICON_SIZE = 16

        var psiManager: PsiManager? = null
            private set

        fun createIcon(element: PsiElement): Icon? {
            return try {
                var result: Icon? = null
                if (element is PsiFile) {
                    psiManager = element.manager
                    result = createIconInner(element)
                }
                psiManager = null
                result
            } catch (e: Exception) {
                e.printStackTrace(System.out)
                null
            } finally {
                psiManager = null
            }
        }

        fun createIconInner(element: PsiFile): Icon? {
            val resourceResolver = getResourceResolver(element)
            return GutterIconFactory.createIcon(element.virtualFile.path, resourceResolver, ICON_SIZE, ICON_SIZE)
                    ?: getIcon(element.virtualFile, resourceResolver)
        }

        fun createDrawable(element: PsiFile): Drawable? {
            return handleElement(element.virtualFile, getResourceResolver(element))
        }

        private fun getIcon(virtualFile: VirtualFile, resolver: ResourceResolver?): Icon? {
            return handleElement(virtualFile, resolver)?.let { drawable ->
                UIUtil.createImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB).let { image ->
                    drawable.draw(image)

                    if (UIUtil.isRetina()) {
                        val retinaIcon = getRetinaIcon(image)
                        if (retinaIcon != null) {
                            return retinaIcon
                        }
                    }

                    return ImageIcon(image)
                }
            } ?: throw UnsupportedOperationException()
        }

        private fun getResourceResolver(element: PsiFile): ResourceResolver? {
            return ProjectRootManager.getInstance(element.project).fileIndex.getModuleForFile(element.virtualFile)?.let {
                ConfigurationManager.getOrCreateInstance(it).getConfiguration(element.virtualFile).resourceResolver
            }
        }

        private fun handleElement(virtualFile: VirtualFile, resolver: ResourceResolver?): Drawable? {
            return virtualFile.path.takeIf { it.endsWith(XML_TYPE) && it.contains(DRAWABLES_FOLDER_TYPE) }
                    ?.let { createXmlIcon(virtualFile, resolver) }
        }

        private fun createXmlIcon(virtualFile: VirtualFile, resolver: ResourceResolver?): Drawable? {
            val documentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val document = documentBuilder.parse(File(virtualFile.path))

            val root = document.documentElement ?: return null

            if (resolver != null) {
                replaceResourceReferences(root, resolver)
            }

            return DrawableInflater.getDrawable(root)
        }

        private fun getRetinaIcon(image: BufferedImage) =
                takeIf { UIUtil.isRetina() }?.let { ImageUtils.convertToRetina(image) }?.let { RetinaImageIcon(it) }

        private fun replaceResourceReferences(node: Node, resolver: ResourceResolver) {
            if (node.nodeType == Node.ELEMENT_NODE) {
                node.attributes.forEach { attribute ->
                    val value = attribute.nodeValue
                    if (isReference(value)) {
                        val resolvedValue = ResourceHelper.resolveStringValue(resolver, value)
                        if (!isReference(resolvedValue)) {
                            attribute.nodeValue = resolvedValue
                        }
                    }
                }
            }

            var newNode = node.firstChild
            while (newNode != null) {
                replaceResourceReferences(newNode, resolver)
                newNode = newNode.nextSibling
            }
        }

        private fun isReference(attributeValue: String) = ResourceUrl.parse(attributeValue) != null
    }

    private class RetinaImageIcon(image: Image) : ImageIcon(image, "") {
        @Synchronized
        override fun paintIcon(c: Component, g: Graphics, x: Int, y: Int) {
            UIUtil.drawImage(g, this.image, x, y, null)
        }
    }
}