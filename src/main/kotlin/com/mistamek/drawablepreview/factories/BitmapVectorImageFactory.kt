package com.mistamek.drawablepreview.factories

import com.android.ide.common.resources.ResourceResolver
import com.android.ide.common.vectordrawable.VdPreview
import com.android.resources.ResourceUrl
import com.android.tools.idea.res.ResourceHelper
import com.google.common.base.Charsets
import com.google.common.io.Files
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.mistamek.drawablepreview.drawables.Utils
import com.mistamek.drawablepreview.drawables.forEach
import org.w3c.dom.Node
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.xml.parsers.DocumentBuilderFactory

object BitmapVectorImageFactory {

    fun createImage(path: String, resolver: ResourceResolver?, maxWidth: Int, maxHeight: Int): BufferedImage? {
        return if (path.endsWith(".xml")) {
            createXmlImage(path, resolver, maxWidth)
        } else {
            createBitmapImage(path, maxWidth, maxHeight)
        }
    }

    private fun createXmlImage(path: String, resolver: ResourceResolver?, maxWidth: Int): BufferedImage? {
        try {
            val imageTargetSize = VdPreview.TargetSize.createSizeFromWidth(maxWidth)

            val xml = getXmlContent(path)
            // See if this drawable is a vector; we can't render other drawables yet.
            // TODO: Consider resolving selectors to render for example the default image!
            if (xml.contains("<vector")) {
                val documentBuilderFactory = DocumentBuilderFactory.newInstance()
                val documentBuilder = documentBuilderFactory.newDocumentBuilder()
                val document = documentBuilder.parse(File(path)) ?: return null
                val root = document.documentElement ?: return null
                if (resolver != null) {
                    replaceResourceReferences(root, resolver)
                }
                val builder = StringBuilder(100)
                return VdPreview.getPreviewFromVectorDocument(imageTargetSize, document, builder)
            }
        } catch (e: Throwable) {
        }

        return null
    }

    private fun getXmlContent(path: String): String {
        val file = LocalFileSystem.getInstance().findFileByPath(path) ?: return Files.toString(File(path), Charsets.UTF_8)
        val document = FileDocumentManager.getInstance().getCachedDocument(file) ?: return String(file.contentsToByteArray())
        return document.text
    }

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

    private fun createBitmapImage(path: String, maxWidth: Int, maxHeight: Int): BufferedImage? {
        return try {
            ImageIO.read(File(path)).let {
                val output = BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB)
                Utils.drawResizedIcon(it, output)
                return output
            }
        } catch (e: Throwable) {
            null
        }
    }
}