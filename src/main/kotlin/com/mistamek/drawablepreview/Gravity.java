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

package com.mistamek.drawablepreview;

/**
 * Standard constants and tools for placing an object within a potentially
 * larger container.
 */
public class Gravity
{
    /** Constant indicating that no gravity has been set **/
    public static final int NO_GRAVITY = 0x0000;
    
    /** Raw bit indicating the gravity for an axis has been specified. */
    public static final int AXIS_SPECIFIED = 0x0001;

    /** Raw bit controlling how the left/top edge is placed. */
    public static final int AXIS_PULL_BEFORE = 0x0002;
    /** Raw bit controlling how the right/bottom edge is placed. */
    public static final int AXIS_PULL_AFTER = 0x0004;
    /** Raw bit controlling whether the right/bottom edge is clipped to its
     * container, based on the gravity direction being applied. */
    public static final int AXIS_CLIP = 0x0008;

    /** Bits defining the horizontal axis. */
    public static final int AXIS_X_SHIFT = 0;
    /** Bits defining the vertical axis. */
    public static final int AXIS_Y_SHIFT = 4;

    /** Push object to the top of its container, not changing its size. */
    public static final int TOP = (AXIS_PULL_BEFORE|AXIS_SPECIFIED)<<AXIS_Y_SHIFT;
    /** Push object to the bottom of its container, not changing its size. */
    public static final int BOTTOM = (AXIS_PULL_AFTER|AXIS_SPECIFIED)<<AXIS_Y_SHIFT;
    /** Push object to the left of its container, not changing its size. */
    public static final int LEFT = (AXIS_PULL_BEFORE|AXIS_SPECIFIED)<<AXIS_X_SHIFT;
    /** Push object to the right of its container, not changing its size. */
    public static final int RIGHT = (AXIS_PULL_AFTER|AXIS_SPECIFIED)<<AXIS_X_SHIFT;

    /** Place object in the vertical center of its container, not changing its
     *  size. */
    public static final int CENTER_VERTICAL = AXIS_SPECIFIED<<AXIS_Y_SHIFT;
    /** Grow the vertical size of the object if needed so it completely fills
     *  its container. */
    public static final int FILL_VERTICAL = TOP|BOTTOM;

    /** Place object in the horizontal center of its container, not changing its
     *  size. */
    public static final int CENTER_HORIZONTAL = AXIS_SPECIFIED<<AXIS_X_SHIFT;
    /** Grow the horizontal size of the object if needed so it completely fills
     *  its container. */
    public static final int FILL_HORIZONTAL = LEFT|RIGHT;

    /** Place the object in the center of its container in both the vertical
     *  and horizontal axis, not changing its size. */
    public static final int CENTER = CENTER_VERTICAL|CENTER_HORIZONTAL;

    /** Grow the horizontal and vertical size of the object if needed so it
     *  completely fills its container. */
    public static final int FILL = FILL_VERTICAL|FILL_HORIZONTAL;

    /** Flag to clip the edges of the object to its container along the
     *  vertical axis. */
    public static final int CLIP_VERTICAL = AXIS_CLIP<<AXIS_Y_SHIFT;
    
    /** Flag to clip the edges of the object to its container along the
     *  horizontal axis. */
    public static final int CLIP_HORIZONTAL = AXIS_CLIP<<AXIS_X_SHIFT;

    /** Raw bit controlling whether the layout direction is relative or not (START/END instead of
     * absolute LEFT/RIGHT).
     */
    public static final int RELATIVE_LAYOUT_DIRECTION = 0x00800000;

    /**
     * Binary mask to get the absolute horizontal gravity of a gravity.
     */
    public static final int HORIZONTAL_GRAVITY_MASK = (AXIS_SPECIFIED |
            AXIS_PULL_BEFORE | AXIS_PULL_AFTER) << AXIS_X_SHIFT;
    /**
     * Binary mask to get the vertical gravity of a gravity.
     */
    public static final int VERTICAL_GRAVITY_MASK = (AXIS_SPECIFIED |
            AXIS_PULL_BEFORE | AXIS_PULL_AFTER) << AXIS_Y_SHIFT;

    
    /** Push object to x-axis position at the start of its container, not changing its size. */
    public static final int START = RELATIVE_LAYOUT_DIRECTION | LEFT;

    /** Push object to x-axis position at the end of its container, not changing its size. */
    public static final int END = RELATIVE_LAYOUT_DIRECTION | RIGHT;

    /**
     * Binary mask for the horizontal gravity and script specific direction bit.
     */
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = START | END;


}
