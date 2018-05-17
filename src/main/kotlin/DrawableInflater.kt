import drawables.Drawable
import drawables.InsetDrawable
import drawables.LayerDrawable
import org.w3c.dom.Element

class DrawableInflater {
    companion object {

        private const val INSET_DRAWABLE = "inset"
        private const val LAYER_DRAWABLE = "layer-list"

        fun getDrawable(element: Element): Drawable? {
            val drawable = createDrawable(element)
            drawable?.let {
                it.inflate(element)
            }
            return drawable
        }

        private fun createDrawable(element: Element): Drawable? {
            return when (element.tagName) {
                INSET_DRAWABLE -> InsetDrawable()
                LAYER_DRAWABLE -> LayerDrawable()
                else -> null
            }
        }
    }
}