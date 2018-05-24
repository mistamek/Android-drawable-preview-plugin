package drawables

import IconPreviewFactory
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import java.awt.Color
import java.io.File
import java.util.*

class ParseUtils {
    companion object {

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

        fun parseAttributeAsColor(string: String, defaultColor: Color?): Color? {
            return try {
                Color.decode(string.replace("#", "0x"))
            } catch (e: Exception) {
                defaultColor
            }
        }

        fun getPsiFileFromPath(path: String): PsiFile? {
            val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(path))
            return virtualFile?.let { IconPreviewFactory.psiManager?.findFile(it) }
        }
    }
}