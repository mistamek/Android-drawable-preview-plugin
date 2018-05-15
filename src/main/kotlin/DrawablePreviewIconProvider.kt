import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import javax.swing.Icon

class DrawablePreviewIconProvider : IconProvider() {

    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        return IconPreviewFactory.createIcon(element)
    }
}