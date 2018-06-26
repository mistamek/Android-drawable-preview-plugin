package drawables.dom

import com.intellij.util.ui.UIUtil
import drawables.ItemDrawableInflater
import drawables.Utils
import org.w3c.dom.Element
import java.awt.RenderingHints
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
        degrees = Utils.parseAttributeAsInt(element.getAttribute(FROM_DEGREES), degrees)
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)
        drawable?.also { drawable ->
            val newImage = UIUtil.createImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
            drawable.draw(newImage)

            AffineTransform().also { rotateTransform ->
                rotateTransform.setTransform(AffineTransform())
                rotateTransform.rotate(Math.toRadians(degrees.toDouble()), image.width / 2.0, image.height / 2.0)

                image.createGraphics().apply {
                    setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    drawImage(newImage, rotateTransform, null)
                    dispose()
                }
            }
        }
    }
}