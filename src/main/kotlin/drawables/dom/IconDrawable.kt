package drawables.dom

import drawables.Utils
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon

class IconDrawable : Drawable() {

    var icon: Icon? = null

    override fun draw(image: BufferedImage) {
        super.draw(image)
        icon?.let {
            if (it is ImageIcon) {
                Utils.drawResizedIcon(it.image, image)
            }
        }
    }
}