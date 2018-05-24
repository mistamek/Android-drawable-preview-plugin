package drawables.dom

import java.awt.Color
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon

class IconDrawable : Drawable() {

    var icon: Icon? = null

    override fun draw(image: BufferedImage) {
        super.draw(image)
        icon?.let {
            if (it is ImageIcon) {
                val originalWidth = it.iconWidth
                val originalHeight = it.iconHeight
                val boundWidth = image.width
                val boundHeight = image.height
                var newWidth = originalWidth
                var newHeight = originalHeight

                // first check if we need to scale width
                if (originalWidth > boundWidth) {
                    //scale width to fit
                    newWidth = boundWidth
                    //scale height to maintain aspect ratio
                    newHeight = newWidth * originalHeight / originalWidth
                }

                // then check if we need to scale even with the new height
                if (newHeight > boundHeight) {
                    //scale height to fit instead
                    newHeight = boundHeight
                    //scale width to maintain aspect ratio
                    newWidth = newHeight * originalWidth / originalHeight
                }

                val paddingLeft = Math.round((boundWidth - newWidth) / 2F)
                val paddingTop = Math.round((boundHeight - newHeight) / 2F)

                image.graphics.drawImage(it.image, paddingLeft, paddingTop, newWidth, newHeight, null)
            }
        }
    }
}