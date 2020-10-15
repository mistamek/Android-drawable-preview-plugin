package com.mistamek.drawablepreview.drawables

import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import com.mistamek.drawablepreview.Gravity
import com.mistamek.drawablepreview.factories.IconPreviewFactory
import java.awt.Color
import java.awt.Image
import java.io.File
import java.util.*
import kotlin.math.round

object Utils {

    private const val LEFT = "left"
    private const val TOP = "top"
    private const val BOTTOM = "bottom"
    private const val RIGHT = "right"
    private const val CENTER_VERTICAL = "center_vertical"
    private const val FILL_VERTICAL = "fill_vertical"
    private const val CENTER_HORIZONTAL = "center_horizontal"
    private const val FILL_HORIZONTAL = "fill_horizontal"
    private const val CENTER = "center"
    private const val FILL = "fill"
    private const val CLIP_VERTICAL = "clip_vertical"
    private const val CLIP_HORIZONTAL = "clip_horizontal"
    private const val START = "start"
    private const val END = "end"

    fun parseAttributeAsInt(string: String?, defaultValue: Int): Int {
        return string?.let { intString ->
            return try {
                Scanner(intString).useDelimiter("\\D+").nextInt()
            } catch (e: Exception) {
                defaultValue
            }
        } ?: defaultValue
    }

    fun parseAttributeAsFloat(string: String?, defaultValue: Float): Float {
        return string?.let { floatString ->
            return try {
                Scanner(floatString).useDelimiter("[^0-9.]+").nextFloat()
            } catch (e: Exception) {
                defaultValue
            }
        } ?: defaultValue
    }

    fun parseAttributeAsPercent(string: String?, defaultValue: Float): Float {
        return string?.let { percentString ->
            return try {
                Scanner(percentString).useDelimiter("\\D+").nextInt() / 100F
            } catch (e: Exception) {
                defaultValue
            }
        } ?: defaultValue
    }

    fun parseAttributeAsColor(string: String?, defaultColor: Color?): Color? {
        return string?.let { colorString ->
            return try {
                var color = colorString.substring(1).toLong(16)
                if (colorString.length == 7) color = color or -16777216L
                return Color(color.toInt(), true)
            } catch (e: Exception) {
                defaultColor
            }
        } ?: defaultColor
    }

    fun parseAttributeAsGravity(string: String?, defaultValue: Int): Int {
        return string?.let { gravityString ->
            return try {
                var value = Gravity.NO_GRAVITY
                gravityString.split("|").forEach { gravity ->
                    when (gravity.trim()) {
                        LEFT -> value = value or Gravity.LEFT
                        TOP -> value = value or Gravity.TOP
                        BOTTOM -> value = value or Gravity.BOTTOM
                        RIGHT -> value = value or Gravity.RIGHT
                        CENTER_VERTICAL -> value = value or Gravity.CENTER_VERTICAL
                        FILL_VERTICAL -> value = value or Gravity.FILL_VERTICAL
                        CENTER_HORIZONTAL -> value = value or Gravity.CENTER_HORIZONTAL
                        FILL_HORIZONTAL -> value = value or Gravity.FILL_HORIZONTAL
                        CENTER -> value = value or Gravity.CENTER
                        FILL -> value = value or Gravity.FILL
                        CLIP_VERTICAL -> value = value or Gravity.CLIP_VERTICAL
                        CLIP_HORIZONTAL -> value = value or Gravity.CLIP_HORIZONTAL
                        START -> value = value or Gravity.START
                        END -> value = value or Gravity.END
                    }
                }
                return if (value == Gravity.NO_GRAVITY) defaultValue else value
            } catch (e: Exception) {
                defaultValue
            }
        } ?: defaultValue
    }

    fun getPsiFileFromPath(path: String): PsiFile? {
        val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(path))
        return virtualFile?.let { IconPreviewFactory.psiManager?.findFile(it) }
    }

    fun drawResizedIcon(src: Image, out: Image) {
        val originalWidth = src.getWidth(null)
        val originalHeight = src.getHeight(null)
        val boundWidth = out.getWidth(null)
        val boundHeight = out.getHeight(null)
        var newWidth = originalWidth
        var newHeight = originalHeight

        // first check if we need to scale width
        if (originalWidth > boundWidth) {
            //scale width to fit
            newWidth = boundWidth
            //scale height to maintain aspect ratio
            newHeight = newWidth * originalHeight / originalWidth
        }

        // then check if we need to scale even with the new height
        if (newHeight > boundHeight) {
            //scale height to fit instead
            newHeight = boundHeight
            //scale width to maintain aspect ratio
            newWidth = newHeight * originalWidth / originalHeight
        }

        val paddingLeft = round((boundWidth - newWidth) / 2F).toInt()
        val paddingTop = round((boundHeight - newHeight) / 2F).toInt()

        out.graphics.drawImage(src, paddingLeft, paddingTop, newWidth, newHeight, null)
    }
}