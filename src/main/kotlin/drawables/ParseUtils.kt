package drawables

import IconPreviewFactory
import android.view.Gravity
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import java.awt.Color
import java.io.File
import java.util.*

class ParseUtils {
    companion object {

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

        fun parseAttributeAsInt(string: String, defaultValue: Int): Int {
            return try {
                Scanner(string).useDelimiter("\\D+").nextInt()
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun parseAttributeAsFloat(string: String, defaultValue: Float): Float {
            return try {
                Scanner(string).useDelimiter("\\D+").nextFloat()
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun parseAttributeAsPercent(string: String, defaultValue: Float): Float {
            return try {
                Scanner(string).useDelimiter("\\D+").nextInt() / 100F
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun parseAttributeAsColor(string: String, defaultColor: Color?): Color? {
            return try {
                Color.decode(string.replace("#", "0x"))
            } catch (e: Exception) {
                defaultColor
            }
        }

        fun parseAttributeAsGravity(string: String, defaultValue: Int): Int {
            return try {
                var value = Gravity.NO_GRAVITY
                string.split("|").forEach({
                    when (it.trim()) {
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
                })
                return if (value == Gravity.NO_GRAVITY) defaultValue else value
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun getPsiFileFromPath(path: String): PsiFile? {
            val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(path))
            return virtualFile?.let { IconPreviewFactory.psiManager?.findFile(it) }
        }
    }
}