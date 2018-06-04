package drawables.dom

import com.intellij.util.ui.UIUtil
import drawables.ItemDrawableInflater
import drawables.Utils
import org.w3c.dom.Element
import java.awt.image.BufferedImage

class LayerDrawable : Drawable() {

    companion object {
        private const val ITEM_TAG = "item"
    }

    private val drawables = ArrayList<LayerDrawableItem>()

    override fun inflate(element: Element) {
        super.inflate(element)

        element.childNodes?.also {
            for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element && childNode.tagName?.equals(ITEM_TAG) == true) {
                    drawables.add(LayerDrawableItem((childNode)))
                }
            }
        }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        resolveDimens(image)

        for (drawable in drawables) {
            drawable.draw(image)
        }
    }

    private fun resolveDimens(image: BufferedImage) {
        // resolve size if provided by attr
        var maxAttrSize = drawables.flatMap { listOf(it.height, it.width) }.max()?.toFloat() ?: 0F
        drawables.forEach{
            it.width = (image.width * (it.width / maxAttrSize)).toInt()
            it.height = (image.height * (it.height / maxAttrSize)).toInt()
        }

        val maxSize = Array(2, { 0 })
        drawables.forEach{
            maxSize[0] = Math.max(maxSize[0], it.width)
            maxSize[1] = Math.max(maxSize[1], it.height)
        }
        if (maxSize[0] == 0) {
            maxSize[0] = image.width
        }
        if (maxSize[1] == 0) {
            maxSize[1] = image.height
        }

        drawables.forEach{
            if (it.width == 0) {
                it.width = maxSize[0]
            }

            if (it.height == 0) {
                it.height = maxSize[1]
            }

            if (maxAttrSize <= 0F) {
                maxAttrSize = drawables.flatMap { listOf(it.left, it.top, it.right, it.bottom) }.max()?.toFloat() ?: 0F
            }

            val max = Math.max(image.width, image.height)
            it.left = (max * (it.left / maxAttrSize)).toInt()
            it.top = (max * (it.top / maxAttrSize)).toInt()
            it.right = (max * (it.right / maxAttrSize)).toInt()
            it.bottom = (max * (it.bottom / maxAttrSize)).toInt()
        }
    }
}

class LayerDrawableItem(element: Element) : Drawable() {
    companion object {
        private const val WIDTH = "android:width"
        private const val HEIGHT = "android:height"
        private const val TOP = "android:top"
        private const val LEFT = "android:left"
        private const val RIGHT = "android:right"
        private const val BOTTOM = "android:bottom"
        private const val START = "android:start"
        private const val END = "android:end"
    }

    var width = 0
    var height = 0

    var top = 0
    var left = 0
    var right = 0
    var bottom = 0

    var drawable: Drawable? = null

    init {
        inflate(element)
    }

    override fun inflate(element: Element) {
        super.inflate(element)
        element.getAttribute(WIDTH)?.run { Utils.parseAttributeAsInt(this, width) }?.also { width = it }
        element.getAttribute(HEIGHT)?.run { Utils.parseAttributeAsInt(this, height) }?.also { height = it }

        element.getAttribute(TOP)?.run { Utils.parseAttributeAsInt(this, top) }?.also { top = it }
        element.getAttribute(LEFT)?.run { Utils.parseAttributeAsInt(this, left) }?.also { left = it }
        element.getAttribute(RIGHT)?.run { Utils.parseAttributeAsInt(this, right) }?.also { right = it }
        element.getAttribute(BOTTOM)?.run { Utils.parseAttributeAsInt(this, bottom) }?.also { bottom = it }
        element.getAttribute(START)?.run { Utils.parseAttributeAsInt(this, left) }?.also { left = it }
        element.getAttribute(END)?.run { Utils.parseAttributeAsInt(this, right) }?.also { right = it }

        drawable = ItemDrawableInflater.getDrawableWithInflate(element)
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        drawable?.also {
            val imageWithInsets = UIUtil.createImage(width, height, BufferedImage.TYPE_INT_ARGB)
            it.draw(imageWithInsets)
            image.graphics.drawImage(imageWithInsets, left - right, top - bottom, width, height, null)
        }
    }
}