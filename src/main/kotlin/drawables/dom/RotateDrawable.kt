package drawables.dom

import com.intellij.util.ui.UIUtil
import drawables.ItemDrawableInflater
import drawables.Utils
import org.w3c.dom.Element
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

class RotateDrawable : Drawable() {
    companion object {
        private const val FROM_DEGREES = "android:fromDegrees"
    }

    private var drawable: Drawable? = null
    private var degrees = 0

    override fun inflate(element: Element) {
        super.inflate(element)
        drawable = ItemDrawableInflater.getDrawableWithInflate(element)
        element.getAttribute(FROM_DEGREES)?.run { Utils.parseAttributeAsInt(this, degrees) }?.also { degrees = it }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        drawable?.also {
            val newImage = UIUtil.createImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
            it.draw(newImage)
            val identity = AffineTransform()
            val rotateTransform = AffineTransform()
            val graphics = image.createGraphics()
            rotateTransform.setTransform(identity)
            rotateTransform.rotate(Math.toRadians(degrees.toDouble()), image.width / 2.0, image.height / 2.0)
            graphics.drawImage(newImage, rotateTransform, null)
        }
    }
}