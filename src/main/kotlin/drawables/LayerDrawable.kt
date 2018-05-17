package drawables

import android.graphics.drawable.LayerDrawable
import org.w3c.dom.Element

class LayerDrawable : DrawableWrapper() {

    companion object {
        private const val PADDING_TOP = "android:paddingTop"
        private const val PADDING_BOTTOM = "android:paddingBottom"
        private const val PADDING_LEFT = "android:paddingLeft"
        private const val PADDING_RIGHT = "android:paddingRight"
        private const val PADDING_END = "android:paddingEnd"
        private const val PADDING_START = "android:paddingStart"

        private const val PADDING_MODE = "android:paddingMode"
        private const val PADDING_MODE_STACK = "stack"
    }

    private var paddingLeft = 0
    private var paddingTop = 0
    private var paddingRight = 0
    private var paddingBottom = 0

    private var paddingMode = LayerDrawable.PADDING_MODE_NEST

    override fun inflate(element: Element) {
        super.inflate(element)

        element.attributes?.also {
            it.getNamedItem(PADDING_MODE)?.run {
                paddingMode = when (this.nodeValue) {
                    PADDING_MODE_STACK -> LayerDrawable.PADDING_MODE_STACK
                    else -> LayerDrawable.PADDING_MODE_NEST
                }
            }

            it.getNamedItem(PADDING_TOP)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { paddingTop = it }
            it.getNamedItem(PADDING_BOTTOM)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { paddingBottom = it }
            it.getNamedItem(PADDING_LEFT)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { paddingLeft = it }
            it.getNamedItem(PADDING_RIGHT)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { paddingRight = it }

            it.getNamedItem(PADDING_START)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { paddingLeft = it }
            it.getNamedItem(PADDING_END)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { paddingRight = it }
        }
    }
}