package drawables.dom

import android.view.Gravity
import com.intellij.util.ui.UIUtil
import drawables.ItemDrawableInflater
import drawables.Utils
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class ScaleDrawable : Drawable() {

    companion object {
        private const val SCALE_HEIGHT = "android:scaleHeight"
        private const val SCALE_WIDTH = "android:scaleWidth"
        private const val SCALE_GRAVITY = "android:scaleGravity"
    }

    private var drawable: Drawable? = null
    private var scaleWidth = 1F
    private var scaleHeight = 1F
    private var gravity = Gravity.LEFT or Gravity.TOP

    override fun inflate(element: Element) {
        super.inflate(element)
        drawable = ItemDrawableInflater.getDrawableWithInflate(element)

        element.getAttribute(SCALE_HEIGHT)?.run { Utils.parseAttributeAsPercent(this, scaleHeight) }?.also { scaleHeight = it }
        element.getAttribute(SCALE_WIDTH)?.run { Utils.parseAttributeAsPercent(this, scaleWidth) }?.also { scaleWidth = it }
        element.getAttribute(SCALE_GRAVITY)?.run { Utils.parseAttributeAsGravity(this, gravity) }?.also { gravity = it }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)

        drawable?.also {
            val width = Math.round(image.width * scaleWidth)
            val height = Math.round(image.height * scaleHeight)
            if (width <= 0 || height <= 0) {
                return
            }

            val resolvedGravity = resolveGravity(image.height, height, image.width, width)

            val scaledImage = UIUtil.createImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
            it.draw(scaledImage)
            image.graphics.apply {
                drawImage(scaledImage, resolvedGravity.first, resolvedGravity.second, width, height, null)
                dispose()
            }
        }
    }

    private fun resolveGravity(parentHeight: Int, containerHeight: Int, parentWidth: Int, containerWidth: Int): Pair<Int, Int> {
        var xValue =
                when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.LEFT, Gravity.START -> 0
                    Gravity.CENTER_HORIZONTAL -> (parentWidth - containerWidth) / 2
                    Gravity.RIGHT, Gravity.END -> parentWidth - containerWidth
                    else -> 0
                }
        var yValue =
                when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
                    Gravity.TOP -> 0
                    Gravity.CENTER_VERTICAL -> (parentHeight - containerHeight) / 2
                    Gravity.BOTTOM -> parentHeight - containerHeight
                    else -> 0
                }

        if (xValue < 0) xValue = 0
        if (yValue < 0) yValue = 0

        return Pair(xValue, yValue)
    }
}