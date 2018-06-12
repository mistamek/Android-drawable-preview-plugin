package drawables

import drawables.dom.*
import org.w3c.dom.Element

class DrawableInflater {
    companion object {

        private const val SELECTOR_DRAWABLE = "selector"
        private const val ANIMATED_SELECTOR_DRAWABLE = "animated-selector"
        private const val LEVEL_LIST_DRAWABLE = "level-list"
        private const val LAYER_DRAWABLE = "layer-list"
        private const val TRANSITION_DRAWABLE = "transition"
        private const val RIPPLE = "ripple"
        private const val ADAPTIVE_ICON = "adaptive-icon"
        private const val COLOR_DRAWABLE = "color"
        private const val SHAPE_DRAWABLE = "shape"
        private const val SCALE = "scale"
        private const val CLIP = "clip"
        private const val ROTATE = "rotate"
        private const val ANIMATED_ROTATE = "animated-rotate"
        private const val INSET_DRAWABLE = "inset"
        private const val BITMAP = "bitmap"

        fun getDrawable(element: Element): Drawable? {
            val drawable = createDrawable(element)
            drawable?.let {
                it.inflate(element)
            }
            return drawable
        }

        private fun createDrawable(element: Element): Drawable? {
            return when (element.tagName) {
                SELECTOR_DRAWABLE -> SelectorDrawable()
                ANIMATED_SELECTOR_DRAWABLE -> SelectorDrawable()
                LEVEL_LIST_DRAWABLE -> LevelListDrawable()
                LAYER_DRAWABLE -> LayerDrawable()
                TRANSITION_DRAWABLE -> LayerDrawable()
                RIPPLE -> RippleDrawable()
                ADAPTIVE_ICON -> AdaptiveIconDrawable()
                COLOR_DRAWABLE -> ColorDrawable()
                SHAPE_DRAWABLE -> GradientDrawable()
                SCALE -> ScaleDrawable()
                CLIP -> ItemDrawableInflater.getDrawable(element)
                ROTATE -> RotateDrawable()
                ANIMATED_ROTATE -> RotateDrawable()
                INSET_DRAWABLE -> InsetDrawable()
                BITMAP -> BitmapDrawable()
                else -> null
            }
        }
    }
}