import drawables.*
import org.w3c.dom.Element

class DrawableInflater {
    companion object {

        private const val INSET_DRAWABLE = "inset"
        private const val LAYER_DRAWABLE = "layer-list"
        private const val SELECTOR_DRAWABLE = "selector"
        private const val ANIMATED_SELECTOR_DRAWABLE = "animated-selector"
        private const val LEVEL_LIST_DRAWABLE = "level-list"
        private const val TRANSITION_DRAWABLE = "transition"
        private const val COLOR_DRAWABLE = "color"
        private const val SHAPE_DRAWABLE = "shape"

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
                TRANSITION_DRAWABLE -> LayerDrawable()
                SELECTOR_DRAWABLE -> DrawableWrapper()
                ANIMATED_SELECTOR_DRAWABLE -> DrawableWrapper()
                LEVEL_LIST_DRAWABLE -> DrawableWrapper()
                COLOR_DRAWABLE -> ColorDrawable()
                SHAPE_DRAWABLE -> GradientDrawable()
                else -> null
            }
        }
    }
}