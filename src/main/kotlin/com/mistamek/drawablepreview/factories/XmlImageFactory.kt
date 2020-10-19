package com.mistamek.drawablepreview.factories

import com.android.ide.common.rendering.api.ResourceNamespace
import com.android.ide.common.rendering.api.ResourceValue
import com.android.ide.common.rendering.api.ResourceValueImpl
import com.android.ide.common.resources.ResourceResolver
import com.android.ide.common.vectordrawable.VdPreview
import com.android.resources.ResourceType
import com.android.resources.ResourceUrl
import com.android.tools.idea.configurations.ConfigurationManager
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiFile
import com.mistamek.drawablepreview.drawables.DrawableInflater
import com.mistamek.drawablepreview.drawables.Utils
import com.mistamek.drawablepreview.drawables.dom.Drawable
import com.mistamek.drawablepreview.drawables.forEach
import com.mistamek.drawablepreview.settings.SettingsUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.awt.image.BufferedImage
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

object XmlImageFactory {

    fun createXmlImage(path: String): BufferedImage? {
        val document = parseDocument(path) ?: return null

        return getDrawableImage(document.documentElement)
                ?: StringBuilder(100).let { builder ->
                    val imageTargetSize: VdPreview.TargetSize? = VdPreview.TargetSize::class.java.let { clazz ->
                        clazz.methods.forEach { method ->
                            when (method.name) {
                                "createSizeFromWidth", "createFromMaxDimension" ->
                                    return@let method.invoke(null, SettingsUtils.getPreviewSize()) as? VdPreview.TargetSize
                            }
                        }
                        null
                    }
                    imageTargetSize?.let { VdPreview.getPreviewFromVectorDocument(imageTargetSize, document, builder) }
                }
    }

    fun getDrawable(path: String): Drawable? = parseDocument(path)?.let { DrawableInflater.getDrawable(it.documentElement) }

    private fun parseDocument(path: String): Document? {
        val supportedFolder = Constants.SUPPORTED_FOLDERS.fold(false) { acc, next -> acc || path.contains(next) }
        if (!(path.endsWith(Constants.XML_TYPE) && supportedFolder)) {
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
            return@let BufferedImage(SettingsUtils.getPreviewSize(), SettingsUtils.getPreviewSize(), BufferedImage.TYPE_INT_ARGB).also { image ->
                drawable.draw(image)
            }
        }
    }

    private fun replaceResourceReferences(node: Node, resolver: ResourceResolver) {
        if (node.nodeType == Node.ELEMENT_NODE) {
            node.attributes.forEach { attribute ->
                val value = attribute.nodeValue
                if (isReference(value)) {
                    val resolvedValue = resolveStringValue(resolver, value)
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

    private fun resolveStringValue(resolver: ResourceResolver, value: String): String {
        val resValue = findResValue(resolver, value) ?: return value
        return resolveNullableResValue(resolver, resValue)?.value ?: value
    }

    private fun findResValue(resolver: ResourceResolver, value: String): ResourceValue? {
        return resolver.dereference(ResourceValueImpl(ResourceNamespace.RES_AUTO, ResourceType.ID, "com.android.ide.common.rendering.api.RenderResources", value))
    }

    private fun resolveNullableResValue(resolver: ResourceResolver, res: ResourceValue?): ResourceValue? {
        if (res == null) {
            return null
        }
        return resolver.resolveResValue(res)
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