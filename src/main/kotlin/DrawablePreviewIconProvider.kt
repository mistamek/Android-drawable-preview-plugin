import com.android.tools.idea.configurations.ConfigurationManager
import com.android.tools.idea.rendering.GutterIconFactory
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

class DrawablePreviewIconProvider : IconProvider() {

    companion object {
        private const val IMAGE_SIZE = 16
    }

    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if (element is PsiFile) {
            val module = ProjectRootManager.getInstance(element.project).fileIndex.getModuleForFile(element.virtualFile)
            module?.let {
                val configuration = ConfigurationManager.getOrCreateInstance(it).getConfiguration(element.virtualFile)
                val resourceResolver = configuration.resourceResolver
                return GutterIconFactory.createIcon(element.virtualFile.path, resourceResolver, IMAGE_SIZE, IMAGE_SIZE)
            }
        }
        return null
    }
}