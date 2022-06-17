package com.mistamek.drawablepreview.factories

import com.intellij.util.ui.UIUtil

object Constants {
    val ICON_SIZE = if (UIUtil.isRetina()) 36 else 16
    const val XML_TYPE = ".xml"
    const val SVG_TYPE = ".svg"
    val SUPPORTED_FOLDERS = arrayOf("drawable", "mipmap")
}