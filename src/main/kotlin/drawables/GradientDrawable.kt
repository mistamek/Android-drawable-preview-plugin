package drawables

import android.graphics.drawable.GradientDrawable
import org.w3c.dom.Element
import java.awt.Color

class GradientDrawable : Drawable() {

    // TODO Add tint

    companion object {
        private const val SHAPE = "android:shape"

        private const val INNER_RADIUS = "android:innerRadius"
        private const val INNER_RADIUS_RATIO = "android:innerRadiusRatio"

        private const val THICKNESS = "android:thickness"
        private const val THICKNESS_RATIO = "android:thicknessRatio"

        private const val SIZE = "size"
        private const val WIDTH = "android:width"
        private const val HEIGHT = "android:height"

        private const val GRADIENT = "gradient"
        private const val ANGLE = "android:angle"
        private const val CENTER_COLOR = "android:centerColor"
        private const val CENTER_X = "android:centerX"
        private const val CENTER_Y = "android:centerY"
        private const val END_COLOR = "android:endColor"
        private const val GRADIENT_RADIUS = "android:gradientRadius"
        private const val START_COLOR = "android:startColor"
        private const val TYPE = "android:type"

        private const val SOLID = "solid"
        private const val COLOR = "android:color"

        private const val STROKE = "stroke"
        private const val DASH_GAP = "android:dashGap"
        private const val DASH_WIDTH = "android:dashWidth"

        private const val CORNERS = "corners"
        private const val RADIUS = "android:radius"
        private const val TOP_LEFT_RADIUS = "android:topLeftRadius"
        private const val BOTTOM_LEFT_RADIUS = "android:bottomLeftRadius"
        private const val BOTTOM_RIGHT_RADIUS = "android:bottomRightRadius"
        private const val TOP_RIGHT_RADIUS = "android:topRightRadius"

        private const val PADDING = "padding"
        private const val PADDING_TOP = "android:paddingTop"
        private const val PADDING_BOTTOM = "android:paddingBottom"
        private const val PADDING_LEFT = "android:paddingLeft"
        private const val PADDING_RIGHT = "android:paddingRight"

        private const val DEFAULT_INT_VALUE = -1
        private const val DEFAULT_INNER_RADIUS_RATIO = 9F
        private const val DEFAULT_THICKNESS_RATIO = 9F

        private const val DEFAULT_GRADIENT_CENTER = 0.5F
    }

    private var shape = GradientDrawable.RECTANGLE
    private var innerRadius = DEFAULT_INT_VALUE
    private var innerRadiusRatio = DEFAULT_INNER_RADIUS_RATIO

    private var thickness = DEFAULT_INT_VALUE
    private var thicknessRatio = DEFAULT_THICKNESS_RATIO

    private var width = DEFAULT_INT_VALUE
    private var height = DEFAULT_INT_VALUE

    private var gradientCenterX = DEFAULT_GRADIENT_CENTER
    private var gradientCenterY = DEFAULT_GRADIENT_CENTER
    private var gradientType = GradientDrawable.LINEAR_GRADIENT

    private var startGradientColor: Color? = null
    private var centerGradientColor: Color? = null
    private var endGradientColor: Color? = null
    private var gradientAngle = 0
    private var gradientRadius = 0.5F

    private var color: Color? = null

    private var strokeColor: Color? = null
    private var strokeWidth = DEFAULT_INT_VALUE
    private var dashGap = DEFAULT_INT_VALUE
    private var dashGapWidth = DEFAULT_INT_VALUE

    private var topLeftRadius = 0f
    private var topRightRadius = 0f
    private var bottomLeftRadius = 0f
    private var bottomRightRadius = 0f

    private var paddingLeft = 0
    private var paddingTop = 0
    private var paddingRight = 0
    private var paddingBottom = 0

    override fun inflate(element: Element) {
        super.inflate(element)

        element.attributes?.also {
            it.getNamedItem(SHAPE)?.run { ParseUtils.parseAttributeAsInt(this, shape) }?.also { shape = it }

            if (shape == GradientDrawable.RING) {
                it.getNamedItem(INNER_RADIUS)?.run { ParseUtils.parseAttributeAsInt(this, innerRadius) }?.also { innerRadius = it }
                if (innerRadius == DEFAULT_INT_VALUE) {
                    it.getNamedItem(INNER_RADIUS_RATIO)?.run { ParseUtils.parseAttributeAsFloat(this, innerRadiusRatio) }?.also { innerRadiusRatio = it }
                }

                it.getNamedItem(THICKNESS)?.run { ParseUtils.parseAttributeAsInt(this, thickness) }?.also { thickness = it }

                if (thickness == DEFAULT_INT_VALUE) {
                    it.getNamedItem(THICKNESS_RATIO)?.run { ParseUtils.parseAttributeAsFloat(this, thicknessRatio) }?.also { thicknessRatio = it }
                }
            }
        }

        inflateChildElements(element)
    }

    private fun inflateChildElements(element: Element) {
        element.childNodes?.let {
            for (i in 0 until it.length) {
                val childNode = it.item(i)
                if (childNode is Element) {
                    when (childNode.tagName) {
                        SIZE -> updateSize(childNode)
                        GRADIENT -> updateGradient(childNode)
                        SOLID -> updateSolid(childNode)
                        STROKE -> updateStroke(childNode)
                        CORNERS -> updateCorners(childNode)
                        PADDING -> updatePadding(childNode)
                    }
                }
            }
        }
    }

    private fun updateSize(element: Element) {
        element.attributes?.also {
            it.getNamedItem(WIDTH)?.run { ParseUtils.parseAttributeAsInt(this, width) }?.also { width = it }
            it.getNamedItem(HEIGHT)?.run { ParseUtils.parseAttributeAsInt(this, height) }?.also { height = it }
        }
    }

    private fun updateGradient(element: Element) {
        element.attributes?.also {
            it.getNamedItem(CENTER_X)?.run { ParseUtils.parseAttributeAsFloat(this, gradientCenterX) }?.also { gradientCenterX = it }
            it.getNamedItem(CENTER_Y)?.run { ParseUtils.parseAttributeAsFloat(this, gradientCenterY) }?.also { gradientCenterY = it }
            it.getNamedItem(TYPE)?.run { ParseUtils.parseAttributeAsInt(this, gradientType) }?.also { gradientType = it }

            it.getNamedItem(START_COLOR)?.run { ParseUtils.parseAttributeAsColor(this, startGradientColor) }?.also { startGradientColor = it }
            it.getNamedItem(CENTER_COLOR)?.run { ParseUtils.parseAttributeAsColor(this, centerGradientColor) }?.also { centerGradientColor = it }
            it.getNamedItem(END_COLOR)?.run { ParseUtils.parseAttributeAsColor(this, endGradientColor) }?.also { endGradientColor = it }

            it.getNamedItem(ANGLE)?.run { ParseUtils.parseAttributeAsInt(this, gradientAngle) }?.also { gradientAngle = it }
            it.getNamedItem(GRADIENT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, gradientRadius) }?.also { gradientRadius = it }
        }
    }

    private fun updateSolid(element: Element) {
        element.attributes?.also {
            it.getNamedItem(COLOR)?.run { ParseUtils.parseAttributeAsColor(this, color) }?.also { color = it }
        }
    }

    private fun updateStroke(element: Element) {
        element.attributes?.also {
            it.getNamedItem(COLOR)?.run { ParseUtils.parseAttributeAsColor(this, strokeColor) }?.also { strokeColor = it }
            it.getNamedItem(WIDTH)?.run { ParseUtils.parseAttributeAsInt(this, strokeWidth) }?.also { strokeWidth = it }
            it.getNamedItem(DASH_GAP)?.run { ParseUtils.parseAttributeAsInt(this, dashGap) }?.also { dashGap = it }
            it.getNamedItem(DASH_WIDTH)?.run { ParseUtils.parseAttributeAsInt(this, dashGapWidth) }?.also { dashGapWidth = it }
        }
    }

    private fun updateCorners(element: Element) {
        element.attributes?.also {
            it.getNamedItem(RADIUS)?.
                    run { ParseUtils.parseAttributeAsFloat(this, 0F) }?.
                    also { topLeftRadius = it }?.
                    also { topRightRadius = it }?.
                    also { bottomLeftRadius = it }?.
                    also { bottomRightRadius = it }

            it.getNamedItem(TOP_LEFT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, topLeftRadius) }?.also { topLeftRadius = it }
            it.getNamedItem(TOP_RIGHT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, topRightRadius) }?.also { topRightRadius = it }
            it.getNamedItem(BOTTOM_LEFT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, bottomLeftRadius) }?.also { bottomLeftRadius = it }
            it.getNamedItem(BOTTOM_RIGHT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, bottomRightRadius) }?.also { bottomRightRadius = it }
        }
    }

    private fun updatePadding(element: Element) {
        element.attributes?.also {
            it.getNamedItem(PADDING_TOP)?.run { ParseUtils.parseAttributeAsInt(this, paddingTop) }?.also { paddingTop = it }
            it.getNamedItem(PADDING_BOTTOM)?.run { ParseUtils.parseAttributeAsInt(this, paddingBottom) }?.also { paddingBottom = it }
            it.getNamedItem(PADDING_LEFT)?.run { ParseUtils.parseAttributeAsInt(this, paddingLeft) }?.also { paddingLeft = it }
            it.getNamedItem(PADDING_RIGHT)?.run { ParseUtils.parseAttributeAsInt(this, paddingRight) }?.also { paddingRight = it }
        }
    }
}