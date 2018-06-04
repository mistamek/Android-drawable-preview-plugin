package drawables.dom

import com.intellij.util.ui.UIUtil
import drawables.ItemDrawableInflater
import drawables.Utils
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class InsetDrawable : Drawable() {

    companion object {
        private const val INSET = "android:inset"
        private const val INSET_TOP = "android:insetTop"
        private const val INSET_LEFT = "android:insetLeft"
        private const val INSET_RIGHT = "android:insetRight"
        private const val INSET_BOTTOM = "android:insetBottom"
    }

    private var drawable: Drawable? = null

    private var insetTop = 0
    private var insetLeft = 0
    private var insetRight = 0
    private var insetBottom = 0

    override fun inflate(element: Element) {
        super.inflate(element)
        drawable = ItemDrawableInflater.getDrawableWithInflate(element)

        element.getAttribute(INSET)?.
                run { Utils.parseAttributeAsInt(this, 0) }?.
                also { insetTop = it }?.
                also { insetLeft = it }?.
                also { insetRight = it }?.
                also { insetBottom = it }

        element.getAttribute(INSET_TOP)?.run { Utils.parseAttributeAsInt(this, insetTop) }?.also { insetTop = it }
        element.getAttribute(INSET_LEFT)?.run { Utils.parseAttributeAsInt(this, insetLeft) }?.also { insetLeft = it }
        element.getAttribute(INSET_RIGHT)?.run { Utils.parseAttributeAsInt(this, insetRight) }?.also { insetRight = it }
        element.getAttribute(INSET_BOTTOM)?.run { Utils.parseAttributeAsInt(this, insetBottom) }?.also { insetBottom = it }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)

        drawable?.also {
            val maxValueAsFloat = (arrayOf(insetTop, insetLeft, insetRight, insetBottom).max() ?: insetLeft).toFloat()
            val maxInsetSize = image.width / 5
            insetTop = ((insetTop / maxValueAsFloat) * maxInsetSize).toInt()
            insetLeft = ((insetLeft / maxValueAsFloat) * maxInsetSize).toInt()
            insetRight = ((insetRight / maxValueAsFloat) * maxInsetSize).toInt()
            insetBottom = ((insetBottom / maxValueAsFloat) * maxInsetSize).toInt()

            val width = image.width - insetLeft - insetRight
            val height = image.height - insetTop - insetBottom
            if (width <= 0 || height <= 0) {
                return
            }

            val imageWithInsets = UIUtil.createImage(width, height, BufferedImage.TYPE_INT_ARGB)
            it.draw(imageWithInsets)
            image.graphics.drawImage(imageWithInsets, insetLeft, insetTop, width, height, null)
        }
    }
}