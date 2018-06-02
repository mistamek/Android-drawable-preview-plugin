package drawables.dom

import android.graphics.drawable.GradientDrawable
import com.intellij.util.ui.UIUtil
import drawables.ParseUtils
import org.w3c.dom.Element
import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.Path2D
import java.awt.image.BufferedImage


class GradientDrawable : Drawable() {

    companion object {
        private const val SHAPE = "android:shape"
        private const val OVAL_SHAPE = "oval"

        private const val INNER_RADIUS = "android:innerRadius"
        private const val INNER_RADIUS_RATIO = "android:innerRadiusRatio"

        private const val THICKNESS = "android:thickness"
        private const val THICKNESS_RATIO = "android:thicknessRatio"

        private const val SIZE = "size"
        private const val WIDTH = "android:width"
        private const val HEIGHT = "android:height"

        private const val TINT = "android:tint"

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

        private const val DEFAULT_INT_VALUE = -1
        private const val DEFAULT_INNER_RADIUS_RATIO = 9F
        private const val DEFAULT_THICKNESS_RATIO = 9F

        private const val DEFAULT_GRADIENT_CENTER = 0.5F
    }

    private var shape = GradientDrawable.RECTANGLE
    private var innerRadius = DEFAULT_INT_VALUE
    private var innerRadiusRatio = DEFAULT_INNER_RADIUS_RATIO

    private var tintColor: Color? = null

    private var thickness = DEFAULT_INT_VALUE
    private var thicknessRatio = DEFAULT_THICKNESS_RATIO

    private var width = DEFAULT_INT_VALUE
    private var height = DEFAULT_INT_VALUE

    private var resolvedWidth = width
    private var resolvedHeight = height

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

    private var resolvedStrokeWidth = strokeWidth.toFloat()

    private var topLeftRadius = 0f
    private var topRightRadius = 0f
    private var bottomLeftRadius = 0f
    private var bottomRightRadius = 0f

    private var topLeftWidthRadius = 0f
    private var topLeftHeightRadius = 0f
    private var topRightWidthRadius = 0f
    private var topRightHeightRadius = 0f
    private var bottomRightWidthRadius = 0f
    private var bottomRightHeightRadius = 0f
    private var bottomLeftWidthRadius = 0f
    private var bottomLeftHeightRadius = 0f

    override fun inflate(element: Element) {
        super.inflate(element)

        element.getAttribute(TINT)?.run { ParseUtils.parseAttributeAsColor(this, tintColor) }?.also { tintColor = it }
        element.getAttribute(SHAPE)?.let {
            when (it) {
                OVAL_SHAPE -> GradientDrawable.OVAL
                else -> shape
            }
        }?.also { shape = it }

        if (shape == GradientDrawable.RING) {
            element.getAttribute(INNER_RADIUS)?.run { ParseUtils.parseAttributeAsInt(this, innerRadius) }?.also { innerRadius = it }
            if (innerRadius == DEFAULT_INT_VALUE) {
                element.getAttribute(INNER_RADIUS_RATIO)?.run { ParseUtils.parseAttributeAsFloat(this, innerRadiusRatio) }?.also { innerRadiusRatio = it }
            }

            element.getAttribute(THICKNESS)?.run { ParseUtils.parseAttributeAsInt(this, thickness) }?.also { thickness = it }
            if (thickness == DEFAULT_INT_VALUE) {
                element.getAttribute(THICKNESS_RATIO)?.run { ParseUtils.parseAttributeAsFloat(this, thicknessRatio) }?.also { thicknessRatio = it }
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
                    }
                }
            }
        }
    }

    private fun updateSize(element: Element) {
        element.getAttribute(WIDTH)?.run { ParseUtils.parseAttributeAsInt(this, width) }?.also { width = it }
        element.getAttribute(HEIGHT)?.run { ParseUtils.parseAttributeAsInt(this, height) }?.also { height = it }
    }

    private fun updateGradient(element: Element) {
        element.getAttribute(CENTER_X)?.run { ParseUtils.parseAttributeAsFloat(this, gradientCenterX) }?.also { gradientCenterX = it }
        element.getAttribute(CENTER_Y)?.run { ParseUtils.parseAttributeAsFloat(this, gradientCenterY) }?.also { gradientCenterY = it }
        element.getAttribute(TYPE)?.run { ParseUtils.parseAttributeAsInt(this, gradientType) }?.also { gradientType = it }

        element.getAttribute(START_COLOR)?.run { ParseUtils.parseAttributeAsColor(this, startGradientColor) }?.also { startGradientColor = it }
        element.getAttribute(CENTER_COLOR)?.run { ParseUtils.parseAttributeAsColor(this, centerGradientColor) }?.also { centerGradientColor = it }
        element.getAttribute(END_COLOR)?.run { ParseUtils.parseAttributeAsColor(this, endGradientColor) }?.also { endGradientColor = it }

        element.getAttribute(ANGLE)?.run { ParseUtils.parseAttributeAsInt(this, gradientAngle) }?.also { gradientAngle = it }
        element.getAttribute(GRADIENT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, gradientRadius) }?.also { gradientRadius = it }
    }

    private fun updateSolid(element: Element) {
        element.getAttribute(COLOR)?.run { ParseUtils.parseAttributeAsColor(this, color) }?.also { color = it }
    }

    private fun updateStroke(element: Element) {
        element.getAttribute(COLOR)?.run { ParseUtils.parseAttributeAsColor(this, strokeColor) }?.also { strokeColor = it }
        element.getAttribute(WIDTH)?.run { ParseUtils.parseAttributeAsInt(this, strokeWidth) }?.also { strokeWidth = it }
        element.getAttribute(DASH_GAP)?.run { ParseUtils.parseAttributeAsInt(this, dashGap) }?.also { dashGap = it }
        element.getAttribute(DASH_WIDTH)?.run { ParseUtils.parseAttributeAsInt(this, dashGapWidth) }?.also { dashGapWidth = it }
    }

    private fun updateCorners(element: Element) {
        element.getAttribute(RADIUS)?.
                run { ParseUtils.parseAttributeAsFloat(this, 0F) }?.
                also { topLeftRadius = it }?.
                also { topRightRadius = it }?.
                also { bottomLeftRadius = it }?.
                also { bottomRightRadius = it }

        element.getAttribute(TOP_LEFT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, topLeftRadius) }?.also { topLeftRadius = it }
        element.getAttribute(TOP_RIGHT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, topRightRadius) }?.also { topRightRadius = it }
        element.getAttribute(BOTTOM_LEFT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, bottomLeftRadius) }?.also { bottomLeftRadius = it }
        element.getAttribute(BOTTOM_RIGHT_RADIUS)?.run { ParseUtils.parseAttributeAsFloat(this, bottomRightRadius) }?.also { bottomRightRadius = it }
    }

    override fun draw(image: BufferedImage) {
        super.draw(image)

        resolveDimensions(image)

        val resizedImage = UIUtil.createImage(resolvedWidth, resolvedHeight, BufferedImage.TYPE_INT_ARGB)
        val resizedGraphics = resizedImage.createGraphics()
        resizedGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        drawShape(resizedGraphics)
        drawStroke(resizedGraphics)

        image.createGraphics()?.also {
            it.drawImage(resizedImage, (image.width - resolvedWidth) / 2, (image.height - resolvedHeight) / 2, null)
            it.dispose()
        }
        resizedGraphics.dispose()
    }

    private fun resolveDimensions(image: BufferedImage) {
        resolvedWidth = image.width
        resolvedHeight = image.height
        var maxValue: Float? = null

        if (width > 0 && height > 0) {
            maxValue = Math.max(width, height).toFloat()
            resolvedWidth = (image.width * (width / maxValue)).toInt()
            resolvedHeight = (image.height * (height / maxValue)).toInt()
        }
        resolveCorners(resolvedWidth.toFloat(), resolvedHeight.toFloat(), maxValue)
        resolveStroke(resolvedWidth.toFloat(), maxValue)
    }

    private fun resolveCorners(width: Float, height: Float, maxValue: Float?) {
        val maxCorner = arrayOf(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius).max() ?: 0F
        if (maxCorner > 0) {
            val maxValueToUse = maxValue ?: maxCorner

            topLeftHeightRadius = (height * (topLeftRadius / maxValueToUse)).let { Math.min(it, height) }
            topLeftWidthRadius = (width * (topLeftRadius / maxValueToUse)).let { Math.min(it, width) }

            topRightHeightRadius = (height * (topRightRadius / maxValueToUse)).let { Math.min(it, height) }
            topRightWidthRadius = (width * (topRightRadius / maxValueToUse)).let { Math.min(it, width) }

            bottomRightHeightRadius = (height * (bottomRightRadius / maxValueToUse)).let { Math.min(it, height) }
            bottomRightWidthRadius = (width * (bottomRightRadius / maxValueToUse)).let { Math.min(it, width) }

            bottomLeftHeightRadius = (height * (bottomLeftRadius / maxValueToUse)).let { Math.min(it, height) }
            bottomLeftWidthRadius = (width * (bottomLeftRadius / maxValueToUse)).let { Math.min(it, width) }


            (topLeftWidthRadius + topRightWidthRadius).takeIf { it > width }?.also {
                val half = (it - width) / 2
                topLeftWidthRadius -= half
                topRightWidthRadius -= half
            }

            (bottomLeftWidthRadius + bottomRightWidthRadius).takeIf { it > width }?.also {
                val half = (it - width) / 2
                bottomLeftWidthRadius -= half
                bottomRightWidthRadius -= half
            }

            (topLeftHeightRadius + bottomLeftHeightRadius).takeIf { it > height }?.also {
                val half = (it - height) / 2
                topLeftHeightRadius -= half
                bottomLeftHeightRadius -= half
            }

            (topRightHeightRadius + bottomRightHeightRadius).takeIf { it > height }?.also {
                val half = (it - height) / 2
                topRightHeightRadius -= half
                bottomRightHeightRadius -= half
            }
        }
    }

    private fun resolveStroke(width: Float, maxValue: Float?) {
        if (maxValue != null) {
            resolvedStrokeWidth = (width * (strokeWidth / maxValue)).let { Math.min(it, width * 0.5F) }
        } else {
            resolvedStrokeWidth = width * 0.2F
        }
    }

    private fun drawShape(graphics: Graphics2D) {
        graphics.color = color
        val shapeToUse = when (shape) {
            GradientDrawable.OVAL -> getOval()
            GradientDrawable.RECTANGLE -> getRoundPath()
            else -> null
        }
        shapeToUse?.also { graphics.fill(it) }
    }

    private fun drawStroke(graphics: Graphics2D) {
        if (resolvedStrokeWidth > 0) {
            graphics.color = strokeColor
            graphics.stroke = createStroke()
            val shapeToUse = when (shape) {
                GradientDrawable.OVAL -> getOval(true)
                GradientDrawable.RECTANGLE -> getRoundPath(true)
                else -> null
            }
            shapeToUse?.also { graphics.draw(it) }
        }
    }

    private fun createStroke(): Stroke {
        return BasicStroke(resolvedStrokeWidth)
    }

    private fun getOval(forStroke: Boolean = false): Shape {
        return if (forStroke) {
            val halfStroke = resolvedStrokeWidth / 2
            Ellipse2D.Float(halfStroke, halfStroke, resolvedWidth.toFloat() - resolvedStrokeWidth, resolvedHeight.toFloat() - resolvedStrokeWidth)
        } else {
            Ellipse2D.Float(0F, 0F, resolvedWidth.toFloat(), resolvedHeight.toFloat())
        }
    }

    private fun getRoundPath(forStroke: Boolean = false): Shape {
        return Path2D.Float().apply {
            val widthF = resolvedWidth.toFloat()
            val heightF = resolvedHeight.toFloat()

            moveTo(0F, 0F)

            lineTo(widthF - topRightWidthRadius, 0F)
            quadTo(widthF, 0F, widthF, 0F + topRightHeightRadius)

            lineTo(widthF, heightF - bottomRightHeightRadius)
            quadTo(widthF, heightF, widthF - bottomRightWidthRadius, heightF)

            lineTo(0F + bottomLeftWidthRadius, heightF)
            quadTo(0F, heightF, 0F, heightF - bottomLeftHeightRadius)

            lineTo(0F, 0F + topLeftHeightRadius)
            quadTo(0F, 0F, 0F + topLeftWidthRadius, 0F)

            closePath()
        }
    }
}