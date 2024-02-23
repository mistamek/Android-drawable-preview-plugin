package android.view

object Gravity {
    /**
     * Constant indicating that no gravity has been set
     */
    const val NO_GRAVITY: Int = 0x0000

    /**
     * Raw bit indicating the gravity for an axis has been specified.
     */
    const val AXIS_SPECIFIED: Int = 0x0001

    /**
     * Raw bit controlling how the left/top edge is placed.
     */
    const val AXIS_PULL_BEFORE: Int = 0x0002

    /**
     * Raw bit controlling how the right/bottom edge is placed.
     */
    const val AXIS_PULL_AFTER: Int = 0x0004

    /**
     * Raw bit controlling whether the right/bottom edge is clipped to its
     * container, based on the gravity direction being applied.
     */
    const val AXIS_CLIP: Int = 0x0008

    /**
     * Bits defining the horizontal axis.
     */
    const val AXIS_X_SHIFT: Int = 0

    /**
     * Bits defining the vertical axis.
     */
    const val AXIS_Y_SHIFT: Int = 4

    /**
     * Push object to the top of its container, not changing its size.
     */
    const val TOP: Int = (AXIS_PULL_BEFORE or AXIS_SPECIFIED) shl AXIS_Y_SHIFT

    /**
     * Push object to the bottom of its container, not changing its size.
     */
    const val BOTTOM: Int = (AXIS_PULL_AFTER or AXIS_SPECIFIED) shl AXIS_Y_SHIFT

    /**
     * Push object to the left of its container, not changing its size.
     */
    const val LEFT: Int = (AXIS_PULL_BEFORE or AXIS_SPECIFIED) shl AXIS_X_SHIFT

    /**
     * Push object to the right of its container, not changing its size.
     */
    const val RIGHT: Int = (AXIS_PULL_AFTER or AXIS_SPECIFIED) shl AXIS_X_SHIFT

    /**
     * Place object in the vertical center of its container, not changing its
     * size.
     */
    const val CENTER_VERTICAL: Int = AXIS_SPECIFIED shl AXIS_Y_SHIFT

    /**
     * Grow the vertical size of the object if needed so it completely fills
     * its container.
     */
    const val FILL_VERTICAL: Int = TOP or BOTTOM

    /**
     * Place object in the horizontal center of its container, not changing its
     * size.
     */
    const val CENTER_HORIZONTAL: Int = AXIS_SPECIFIED shl AXIS_X_SHIFT

    /**
     * Grow the horizontal size of the object if needed so it completely fills
     * its container.
     */
    const val FILL_HORIZONTAL: Int = LEFT or RIGHT

    /**
     * Place the object in the center of its container in both the vertical
     * and horizontal axis, not changing its size.
     */
    const val CENTER: Int = CENTER_VERTICAL or CENTER_HORIZONTAL

    /**
     * Grow the horizontal and vertical size of the object if needed so it
     * completely fills its container.
     */
    const val FILL: Int = FILL_VERTICAL or FILL_HORIZONTAL

    /**
     * Flag to clip the edges of the object to its container along the
     * vertical axis.
     */
    const val CLIP_VERTICAL: Int = AXIS_CLIP shl AXIS_Y_SHIFT

    /**
     * Flag to clip the edges of the object to its container along the
     * horizontal axis.
     */
    const val CLIP_HORIZONTAL: Int = AXIS_CLIP shl AXIS_X_SHIFT

    /**
     * Raw bit controlling whether the layout direction is relative or not (START/END instead of
     * absolute LEFT/RIGHT).
     */
    const val RELATIVE_LAYOUT_DIRECTION: Int = 0x00800000

    /**
     * Binary mask to get the absolute horizontal gravity of a gravity.
     */
    const val HORIZONTAL_GRAVITY_MASK: Int = (AXIS_SPECIFIED or
            AXIS_PULL_BEFORE or AXIS_PULL_AFTER) shl AXIS_X_SHIFT

    /**
     * Binary mask to get the vertical gravity of a gravity.
     */
    const val VERTICAL_GRAVITY_MASK: Int = (AXIS_SPECIFIED or
            AXIS_PULL_BEFORE or AXIS_PULL_AFTER) shl AXIS_Y_SHIFT

    /**
     * Special constant to enable clipping to an overall display along the
     * vertical dimension.  This is not applied by default by
     * [.apply]; you must do so
     * yourself by calling [.applyDisplay].
     */
    const val DISPLAY_CLIP_VERTICAL: Int = 0x10000000

    /**
     * Special constant to enable clipping to an overall display along the
     * horizontal dimension.  This is not applied by default by
     * [.apply]; you must do so
     * yourself by calling [.applyDisplay].
     */
    const val DISPLAY_CLIP_HORIZONTAL: Int = 0x01000000

    /**
     * Push object to x-axis position at the start of its container, not changing its size.
     */
    const val START: Int = RELATIVE_LAYOUT_DIRECTION or LEFT

    /**
     * Push object to x-axis position at the end of its container, not changing its size.
     */
    const val END: Int = RELATIVE_LAYOUT_DIRECTION or RIGHT

    /**
     * Binary mask for the horizontal gravity and script specific direction bit.
     */
    const val RELATIVE_HORIZONTAL_GRAVITY_MASK: Int = START or END
}