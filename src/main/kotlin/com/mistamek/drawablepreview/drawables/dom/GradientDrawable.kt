package com.mistamek.drawablepreview.drawables.dom

import android.graphics.drawable.GradientDrawable
import com.mistamek.drawablepreview.drawables.Utils
import com.mistamek.drawablepreview.drawables.forEachAsElement
import org.w3c.dom.Element
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.LinearGradientPaint
import java.awt.Paint
import java.awt.RadialGradientPaint
import java.awt.RenderingHints
import java.awt.Shape
import java.awt.Stroke
import java.awt.geom.Area
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Path2D
import java.awt.image.BufferedImage
import kotlin.math.max
import kotlin.math.min

class GradientDrawable : Drawable() {

    companion object {
        private const val SHAPE = "android:shape"
        private const val OVAL_SHAPE = "oval"
        private const val LINE = "line"
        private const val RING = "ring"

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
        private const val RADIAL_GRADIENT_TYPE = "radial"
        private const val SWEEP_GRADIENT_TYPE = "sweep"

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

    private var gradientCenterX = -1F
    private var gradientCenterY = -1F
    private var gradientType = GradientDrawable.LINEAR_GRADIENT

    private var startGradientColor: Color? = null
    private var centerGradientColor: Color? = null
    private var endGradientColor: Color? = null
    private var gradientAngle = 0
    private var gradientRadius = 0.5F

    private var color = Color(0, 0, 0, 0)

    private var strokeColor: Color? = null
    private var strokeWidth = DEFAULT_INT_VALUE
    private var dashGap = 0F
    private var dashGapWidth = 0F

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

        tintColor = Utils.parseAttributeAsColor(element.getAttribute(TINT), tintColor)

        element.getAttribute(SHAPE)?.let {
            when (it) {
                OVAL_SHAPE -> GradientDrawable.OVAL
                LINE -> GradientDrawable.LINE
                RING -> GradientDrawable.RING
                else -> shape
            }
        }?.also { shape = it }

        if (shape == GradientDrawable.RING) {
            innerRadius = Utils.parseAttributeAsInt(element.getAttribute(INNER_RADIUS), innerRadius)
            if (innerRadius == DEFAULT_INT_VALUE) {
                innerRadiusRatio = Utils.parseAttributeAsFloat(element.getAttribute(INNER_RADIUS_RATIO), innerRadiusRatio)
            }

            thickness = Utils.parseAttributeAsInt(element.getAttribute(THICKNESS), thickness)
            if (thickness == DEFAULT_INT_VALUE) {
                thicknessRatio = Utils.parseAttributeAsFloat(element.getAttribute(THICKNESS_RATIO), thicknessRatio)
            }
        }

        inflateChildElements(element)
    }

    private fun inflateChildElements(element: Element) {
        element.childNodes?.forEachAsElement { childNode ->
            when (childNode.tagName) {
                SIZE -> updateSize(childNode)
                GRADIENT -> updateGradient(childNode)
                SOLID -> updateSolid(childNode)
                STROKE -> updateStroke(childNode)
                CORNERS -> updateCorners(childNode)
            }
        }
    }

    private fun updateSize(element: Element) {
        width = Utils.parseAttributeAsInt(element.getAttribute(WIDTH), width)
        height = Utils.parseAttributeAsInt(element.getAttribute(HEIGHT), height)
    }

    private fun updateGradient(element: Element) {
        gradientCenterX = Utils.parseAttributeAsFloat(element.getAttribute(CENTER_X), gradientCenterX)
        gradientCenterY = Utils.parseAttributeAsFloat(element.getAttribute(CENTER_Y), gradientCenterY)

        element.getAttribute(TYPE)?.run {
            when (this) {
                RADIAL_GRADIENT_TYPE -> GradientDrawable.RADIAL_GRADIENT
                SWEEP_GRADIENT_TYPE -> GradientDrawable.SWEEP_GRADIENT
                else -> gradientType
            }
        }?.also { gradientType = it }

        startGradientColor = Utils.parseAttributeAsColor(element.getAttribute(START_COLOR), startGradientColor)
        centerGradientColor = Utils.parseAttributeAsColor(element.getAttribute(CENTER_COLOR), centerGradientColor)
        endGradientColor = Utils.parseAttributeAsColor(element.getAttribute(END_COLOR), endGradientColor)

        gradientAngle = Utils.parseAttributeAsInt(element.getAttribute(ANGLE), gradientAngle)
        gradientRadius = Utils.parseAttributeAsFloat(element.getAttribute(GRADIENT_RADIUS), gradientRadius)
    }

    private fun updateSolid(element: Element) {
        color = Utils.parseAttributeAsColor(element.getAttribute(COLOR), color) ?: color
    }

    private fun updateStroke(element: Element) {
        strokeColor = Utils.parseAttributeAsColor(element.getAttribute(COLOR), strokeColor)
        strokeWidth = Utils.parseAttributeAsInt(element.getAttribute(WIDTH), strokeWidth)
        dashGap = Utils.parseAttributeAsFloat(element.getAttribute(DASH_GAP), dashGap)
        dashGapWidth = Utils.parseAttributeAsFloat(element.getAttribute(DASH_WIDTH), dashGapWidth)
    }

    private fun updateCorners(element: Element) {
        element.getAttribute(RADIUS)
                ?.run { Utils.parseAttributeAsFloat(this, 0F) }
                ?.also {
                    topLeftRadius = it
                    topRightRadius = it
                    bottomLeftRadius = it
                    bottomRightRadius = it
                }

        topLeftRadius = Utils.parseAttributeAsFloat(element.getAttribute(TOP_LEFT_RADIUS), topLeftRadius)
        topRightRadius = Utils.parseAttributeAsFloat(element.getAttribute(TOP_RIGHT_RADIUS), topRightRadius)
        bottomLeftRadius = Utils.parseAttributeAsFloat(element.getAttribute(BOTTOM_LEFT_RADIUS), bottomLeftRadius)
        bottomRightRadius = Utils.parseAttributeAsFloat(element.getAttribute(BOTTOM_RIGHT_RADIUS), bottomRightRadius)
    }

    override fun draw(outputImage: BufferedImage) {
        super.draw(outputImage)

        resolveDimensions(outputImage)

        BufferedImage(resolvedWidth, resolvedHeight, BufferedImage.TYPE_INT_ARGB).also { resizedImage ->
            resizedImage.createGraphics().also { resizedGraphics ->
                resizedGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                drawShape(resizedGraphics)
                drawStroke(resizedGraphics)

                outputImage.createGraphics()?.also { graphics ->
                    graphics.drawImage(resizedImage, (outputImage.width - resolvedWidth) / 2, (outputImage.height - resolvedHeight) / 2, null)
                    graphics.dispose()
                }
                resizedGraphics.dispose()
            }
        }
    }

    private fun resolveDimensions(image: BufferedImage) {
        resolvedWidth = image.width
        resolvedHeight = image.height
        if (shape == GradientDrawable.LINE) {
            resolveStroke(resolvedWidth.toFloat(), null)
            return
        } else if (shape == GradientDrawable.RING) {
            return
        }

        var maxValue: Float? = null

        if (width > 0 && height > 0) {
            maxValue = max(width, height).toFloat()
            resolvedWidth = (image.width * (width / maxValue)).toInt()
            resolvedHeight = (image.height * (height / maxValue)).toInt()
            gradientRadius = (image.height * (gradientRadius / maxValue))
        } else {
            gradientRadius = image.width.toFloat()
        }

        resolveCorners(resolvedWidth.toFloat(), resolvedHeight.toFloat(), maxValue)
        resolveStroke(resolvedWidth.toFloat(), maxValue)
    }

    private fun resolveCorners(width: Float, height: Float, maxValue: Float?) {
        val maxCorner = arrayOf(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius).max()
            .times(4F)
        if (maxCorner > 0) {
            val maxValueToUse = maxValue ?: maxCorner

            topLeftHeightRadius = (height * (topLeftRadius / maxValueToUse)).let { min(it, height) }
            topLeftWidthRadius = (width * (topLeftRadius / maxValueToUse)).let { min(it, width) }

            topRightHeightRadius = (height * (topRightRadius / maxValueToUse)).let { min(it, height) }
            topRightWidthRadius = (width * (topRightRadius / maxValueToUse)).let { min(it, width) }

            bottomRightHeightRadius = (height * (bottomRightRadius / maxValueToUse)).let { min(it, height) }
            bottomRightWidthRadius = (width * (bottomRightRadius / maxValueToUse)).let { min(it, width) }

            bottomLeftHeightRadius = (height * (bottomLeftRadius / maxValueToUse)).let { min(it, height) }
            bottomLeftWidthRadius = (width * (bottomLeftRadius / maxValueToUse)).let { min(it, width) }

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
            resolvedStrokeWidth = (width * (strokeWidth / maxValue)).let { min(it, width * 0.5F) }
            dashGap = (width * (dashGap / maxValue)) * 2
            dashGapWidth = width * (dashGap / maxValue)
        } else {
            resolvedStrokeWidth = width * 0.1F
            if (dashGap > 0 || dashGapWidth > 0) {
                dashGap = resolvedStrokeWidth
                dashGapWidth = resolvedStrokeWidth
            }
        }
    }

    private fun drawShape(graphics: Graphics2D) {
        val gradientColors = arrayOf(startGradientColor, centerGradientColor, endGradientColor).filterNotNull()
        if (gradientColors.isEmpty()) {
            graphics.color = color
        } else {
            graphics.paint = getGradientPaint(arrayOf(startGradientColor ?: Color.BLACK,
                    centerGradientColor ?: Color.BLACK,
                    endGradientColor ?: Color.BLACK))
        }

        val shapeToUse = when (shape) {
            GradientDrawable.OVAL -> getOval()
            GradientDrawable.RECTANGLE -> getRoundPath()
            GradientDrawable.RING -> getRing()
            else -> null
        }
        shapeToUse?.also { graphics.fill(it) }

        graphics.color = null
        graphics.paint = null
    }

    private fun getGradientPaint(gradientColors: Array<Color>): Paint? {
        return when (gradientType) {
            GradientDrawable.LINEAR_GRADIENT -> getLinearGradient(gradientColors)
            GradientDrawable.RADIAL_GRADIENT -> getRadialGradient(gradientColors)
            GradientDrawable.SWEEP_GRADIENT -> getSweepGradient(gradientColors)
            else -> null
        }
    }

    private fun getLinearGradient(gradientColors: Array<Color>): Paint {
        val widthF = resolvedWidth.toFloat()
        val heightF = resolvedHeight.toFloat()
        val resolvedAngle = gradientAngle % 360

        var startX = 0F
        var startY = 0F
        var endX = 0F
        var endY = 0F
        when (resolvedAngle) {
            0 -> {
                endX = widthF
            }
            45 -> {
                startY = heightF
                endX = widthF
            }
            90 -> {
                startY = heightF
            }
            135 -> {
                startX = widthF
                startY = heightF
            }
            180 -> {
                startX = widthF
            }
            225 -> {
                startX = widthF
                endY = heightF
            }
            270 -> {
                endY = heightF
            }
            315 -> {
                endX = widthF
                endY = heightF
            }
        }

        val maxGradientCenter = maxOf(gradientCenterX, gradientCenterY).let {
            when {
                it < 0F -> 0.5F
                it == 0F -> 0.01F
                it >= 1F -> 0.99F
                else -> it
            }
        }

        return LinearGradientPaint(
                startX, startY, endX, endY,
                floatArrayOf(0F, maxGradientCenter, 1F),
                gradientColors)
    }

    private fun getRadialGradient(gradientColors: Array<Color>): Paint {
        return RadialGradientPaint(
                gradientCenterX * resolvedWidth,
                gradientCenterY * resolvedHeight,
                gradientRadius,
                floatArrayOf(0F, 0.5F, 1F),
                gradientColors)
    }

    private fun getSweepGradient(gradientColors: Array<Color>): Paint {
        return RadialGradientPaint(
                0.5F * resolvedWidth,
                0.5F * resolvedHeight,
                resolvedWidth.toFloat(),
                floatArrayOf(0F, 0.5F, 1F),
                gradientColors)
    }

    private fun drawStroke(graphics: Graphics2D) {
        if (resolvedStrokeWidth > 0) {
            graphics.color = strokeColor
            graphics.stroke = createStroke()
            val shapeToUse = when (shape) {
                GradientDrawable.OVAL -> getOval(true)
                GradientDrawable.RECTANGLE -> getRoundPath(true)
                GradientDrawable.LINE -> getLinePath()
                else -> null
            }
            shapeToUse?.also { graphics.draw(it) }
        }
    }

    private fun createStroke(): Stroke {
        if (dashGap == 0F || dashGapWidth == 0F) {
            return BasicStroke(resolvedStrokeWidth)
        }

        return BasicStroke(resolvedStrokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1F,
                floatArrayOf(dashGap, dashGapWidth), 0F)
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
        if (forStroke) {
            return Path2D.Float().apply {
                val widthF = resolvedWidth.toFloat()
                val heightF = resolvedHeight.toFloat()
                val halfStroke = resolvedStrokeWidth / 2

                val left = halfStroke - 0.5F
                val top = halfStroke - 0.5F
                val right = widthF - halfStroke
                val bottom = heightF - halfStroke

                moveTo(right - topRightWidthRadius + halfStroke, top)

                lineTo(right - topRightWidthRadius + halfStroke, top)
                quadTo(right, top, right, top + topRightHeightRadius - halfStroke)

                lineTo(right, bottom - bottomRightHeightRadius + halfStroke)
                quadTo(right, bottom, right - bottomRightWidthRadius + halfStroke, bottom)

                lineTo(left + bottomLeftWidthRadius - halfStroke, bottom)
                quadTo(left, bottom, left, bottom - bottomLeftHeightRadius + halfStroke)

                lineTo(left, top + topLeftHeightRadius - halfStroke)
                quadTo(left, top, left + topLeftWidthRadius - halfStroke, top)

                closePath()
            }
        }

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

    private fun getLinePath(): Shape {
        return (resolvedHeight / 2F).let {
            Line2D.Float(0F, it, resolvedWidth.toFloat(), it)
        }
    }

    private fun getRing(): Shape {
        val widthF = resolvedWidth.toFloat()
        val heightF = resolvedHeight.toFloat()
        val centerX = widthF / 2
        val centerY = heightF / 2
        val outerRadius = widthF / 2.0
        val resolvedThickness = widthF * 0.3F

        val outer = Ellipse2D.Double(
                centerX - outerRadius,
                centerY - outerRadius,
                outerRadius + outerRadius,
                outerRadius + outerRadius)

        val inner = Ellipse2D.Double(
                centerX - outerRadius + resolvedThickness,
                centerY - outerRadius + resolvedThickness,
                outerRadius + outerRadius - resolvedThickness - resolvedThickness,
                outerRadius + outerRadius - resolvedThickness - resolvedThickness)

        return Area(outer).apply {
            subtract(Area(inner))
        }
    }
}