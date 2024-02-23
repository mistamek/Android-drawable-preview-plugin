/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.graphics.drawable

/**
 * A Drawable with a color gradient for buttons, backgrounds, etc.
 *
 *
 * It can be defined in an XML file with the `<shape>` element. For more
 * information, see the guide to [Drawable Resources]({@docRoot}guide/topics/resources/drawable-resource.html).
 *
 * @attr ref android.R.styleable#GradientDrawable_visible
 * @attr ref android.R.styleable#GradientDrawable_shape
 * @attr ref android.R.styleable#GradientDrawable_innerRadiusRatio
 * @attr ref android.R.styleable#GradientDrawable_innerRadius
 * @attr ref android.R.styleable#GradientDrawable_thicknessRatio
 * @attr ref android.R.styleable#GradientDrawable_thickness
 * @attr ref android.R.styleable#GradientDrawable_useLevel
 * @attr ref android.R.styleable#GradientDrawableSize_width
 * @attr ref android.R.styleable#GradientDrawableSize_height
 * @attr ref android.R.styleable#GradientDrawableGradient_startColor
 * @attr ref android.R.styleable#GradientDrawableGradient_centerColor
 * @attr ref android.R.styleable#GradientDrawableGradient_endColor
 * @attr ref android.R.styleable#GradientDrawableGradient_useLevel
 * @attr ref android.R.styleable#GradientDrawableGradient_angle
 * @attr ref android.R.styleable#GradientDrawableGradient_type
 * @attr ref android.R.styleable#GradientDrawableGradient_centerX
 * @attr ref android.R.styleable#GradientDrawableGradient_centerY
 * @attr ref android.R.styleable#GradientDrawableGradient_gradientRadius
 * @attr ref android.R.styleable#GradientDrawableSolid_color
 * @attr ref android.R.styleable#GradientDrawableStroke_width
 * @attr ref android.R.styleable#GradientDrawableStroke_color
 * @attr ref android.R.styleable#GradientDrawableStroke_dashWidth
 * @attr ref android.R.styleable#GradientDrawableStroke_dashGap
 * @attr ref android.R.styleable#GradientDrawablePadding_left
 * @attr ref android.R.styleable#GradientDrawablePadding_top
 * @attr ref android.R.styleable#GradientDrawablePadding_right
 * @attr ref android.R.styleable#GradientDrawablePadding_bottom
 */
object GradientDrawable {
    /**
     * Flag to determine if we should wrap negative gradient angle measurements
     * for API levels that support it
     *
     * @hide
     */
    var sWrapNegativeAngleMeasurements: Boolean = true

    /**
     * Shape is a rectangle, possibly with rounded corners
     */
    const val RECTANGLE: Int = 0

    /**
     * Shape is an ellipse
     */
    const val OVAL: Int = 1

    /**
     * Shape is a line
     */
    const val LINE: Int = 2

    /**
     * Shape is a ring.
     */
    const val RING: Int = 3

    /**
     * Gradient is linear (default.)
     */
    const val LINEAR_GRADIENT: Int = 0

    /**
     * Gradient is circular.
     */
    const val RADIAL_GRADIENT: Int = 1

    /**
     * Gradient is a sweep.
     */
    const val SWEEP_GRADIENT: Int = 2

    /**
     * Radius is in pixels.
     */
    private const val RADIUS_TYPE_PIXELS = 0

    /**
     * Radius is a fraction of the base size.
     */
    private const val RADIUS_TYPE_FRACTION = 1

    /**
     * Radius is a fraction of the bounds size.
     */
    private const val RADIUS_TYPE_FRACTION_PARENT = 2

    /**
     * Default orientation for GradientDrawable
     */
    private val DEFAULT_ORIENTATION = Orientation.TOP_BOTTOM

    private const val DEFAULT_INNER_RADIUS_RATIO = 3.0f
    private const val DEFAULT_THICKNESS_RATIO = 9.0f

    /**
     * Controls how the gradient is oriented relative to the drawable's bounds
     */
    enum class Orientation {
        /**
         * draw the gradient from the top to the bottom
         */
        TOP_BOTTOM,

        /**
         * draw the gradient from the top-right to the bottom-left
         */
        TR_BL,

        /**
         * draw the gradient from the right to the left
         */
        RIGHT_LEFT,

        /**
         * draw the gradient from the bottom-right to the top-left
         */
        BR_TL,

        /**
         * draw the gradient from the bottom to the top
         */
        BOTTOM_TOP,

        /**
         * draw the gradient from the bottom-left to the top-right
         */
        BL_TR,

        /**
         * draw the gradient from the left to the right
         */
        LEFT_RIGHT,

        /**
         * draw the gradient from the top-left to the bottom-right
         */
        TL_BR,
    }
}
