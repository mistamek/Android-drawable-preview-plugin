package com.mistamek.drawablepreview.factories

import com.android.tools.adtui.ImageUtils
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.util.ui.UIUtil
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon

object IconPreviewFactory {
    var psiManager: PsiManager? = null
        private set

    fun createIcon(element: PsiElement): Icon? {
        return try {
            var result: Icon? = null
            if (element is PsiFile) {
                psiManager = element.manager
                result = getIcon(element.virtualFile)
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

    private fun getIcon(virtualFile: VirtualFile): Icon? {
        return getImage(virtualFile)?.let { image ->
            if (UIUtil.isRetina()) {
                val retinaIcon = getRetinaIcon(image)
                if (retinaIcon != null) {
                    return retinaIcon
                }
            }

            return ImageIcon(image)
        }
    }

    fun getImage(element: PsiFile): BufferedImage? {
        return getImage(element.virtualFile)
    }

    private fun getImage(virtualFile: VirtualFile): BufferedImage? {
        virtualFile.path.let { path ->
            return when {
                path.endsWith(Constants.XML_TYPE) -> XmlImageFactory.createXmlImage(path)
                path.endsWith(Constants.SVG_TYPE) -> SvgImageFactory.createSvgImage(path)
                else -> BitmapImageFactory.createBitmapImage(path)
            }
        }
    }

    private fun getRetinaIcon(image: BufferedImage) =
        takeIf { UIUtil.isRetina() }?.let { ImageUtils.convertToRetina(image) }?.let { RetinaImageIcon(it) }
}