package drawables

import org.w3c.dom.Element

class LayerDrawable : DrawableWrapper() {

    private var insetLeft = 0
    private var insetTop = 0
    private var insetRight = 0
    private var insetBottom = 0
    private var insetStart = 0
    private var insetEnd = 0

    private var width = 0
    private var height = 0

    override fun inflate(element: Element) {
        super.inflate(element)


    }
}