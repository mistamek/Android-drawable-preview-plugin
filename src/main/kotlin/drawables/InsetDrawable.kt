package drawables

import org.w3c.dom.Element

class InsetDrawable : DrawableWrapper() {

    companion object {
        private const val INSET = "android:inset"
        private const val INSET_TOP = "android:insetTop"
        private const val INSET_BOTTOM = "android:insetBottom"
        private const val INSET_LEFT = "android:insetLeft"
        private const val INSET_RIGHT = "android:insetRight"
    }

    private var insetBottom = 0
    private var insetTop = 0
    private var insetLeft = 0
    private var insetRight = 0

    override fun inflate(element: Element) {
        super.inflate(element)
        element.attributes?.also {
            it.getNamedItem(INSET)?.
                    run { ParseUtils.parseAttributeAsInt(this) }?.
                    also { insetBottom = it }?.
                    also { insetTop = it }?.
                    also { insetLeft = it }?.
                    also { insetRight = it }

            it.getNamedItem(INSET_TOP)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { insetTop = it }
            it.getNamedItem(INSET_BOTTOM)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { insetBottom = it }
            it.getNamedItem(INSET_LEFT)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { insetLeft = it }
            it.getNamedItem(INSET_RIGHT)?.run { ParseUtils.parseAttributeAsInt(this) }?.also { insetRight = it }
        }
    }
}