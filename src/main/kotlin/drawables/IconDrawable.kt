package drawables

import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon

class IconDrawable : Drawable() {

    var icon: Icon? = null

    override fun draw(image: BufferedImage) {
        super.draw(image)
        icon?.let { 
            if (it is ImageIcon) {
                image.graphics.drawImage(it.image, 0, 0, image.width, image.height, null)
            }
        }
    }
}