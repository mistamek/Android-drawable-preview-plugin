package com.mistamek.drawablepreview.factories

import com.android.ide.common.resources.ResourceResolver
import com.android.ide.common.vectordrawable.VdPreview
import com.android.resources.ResourceUrl
import com.android.tools.idea.configurations.ConfigurationManager
import com.android.tools.idea.res.resolveStringValue
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiFile
import com.mistamek.drawablepreview.drawables.DrawableInflater
import com.mistamek.drawablepreview.drawables.Utils
import com.mistamek.drawablepreview.drawables.dom.Drawable
import com.mistamek.drawablepreview.drawables.forEach
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.awt.image.BufferedImage
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

object XmlImageFactory {

    fun createXmlImage(path: String): BufferedImage? {
        return parseDocument(path)?.let { document ->
            getDrawableImage(document.documentElement)
                    ?: StringBuilder(100).let { builder ->
                val imageTargetSize = VdPreview.TargetSize.createSizeFromWidth(Constants.ICON_SIZE)
                VdPreview.getPreviewFromVectorDocument(imageTargetSize, document, builder)
            }
        }
    }

    fun getDrawable(path: String): Drawable? = parseDocument(path)?.let { DrawableInflater.getDrawable(it.documentElement) }

    private fun parseDocument(path: String): Document? {
        if (!(path.endsWith(Constants.XML_TYPE) && path.contains(Constants.DRAWABLES_FOLDER_TYPE))) {
            return null
        }

        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        val document = documentBuilder.parse(File(path)) ?: return null
        val root = document.documentElement ?: return null
        val resolver = getResourceResolver(Utils.getPsiFileFromPath(path))
        if (resolver != null) {
            replaceResourceReferences(root, resolver)
        }
        return document
    }

    private fun getDrawableImage(rootElement: Element): BufferedImage? {
        return DrawableInflater.getDrawable(rootElement)?.let { drawable ->
            return@let BufferedImage(Constants.ICON_SIZE, Constants.ICON_SIZE, BufferedImage.TYPE_INT_ARGB).also { image ->
                drawable.draw(image)
            }
        }
    }

    private fun replaceResourceReferences(node: Node, resolver: ResourceResolver) {
        if (node.nodeType == Node.ELEMENT_NODE) {
            node.attributes.forEach { attribute ->
                val value = attribute.nodeValue
                if (isReference(value)) {
                    val resolvedValue = resolver.resolveStringValue(value)
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

    private fun getResourceResolver(element: PsiFile?): ResourceResolver? {
        return element?.let {
            ProjectRootManager.getInstance(element.project).fileIndex.getModuleForFile(element.virtualFile)?.let {
                ConfigurationManager.getOrCreateInstance(it).getConfiguration(element.virtualFile).resourceResolver
            }
        }
    }
}