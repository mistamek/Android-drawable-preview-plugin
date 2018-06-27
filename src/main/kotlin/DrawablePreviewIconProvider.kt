
import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement

class DrawablePreviewIconProvider : IconProvider() {

    override fun getIcon(element: PsiElement, flags: Int) = IconPreviewFactory.createIcon(element)
}