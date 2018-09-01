package com.mistamek.drawablepreview.drawables

import org.w3c.dom.Element
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.w3c.dom.NodeList

inline fun NodeList.forEachAsElement(action: (Element) -> Unit) {
    for (i in 0 until length) {
        val childNode = item(i)
        if (childNode is Element) {
            action(childNode)
        }
    }
}

inline fun NamedNodeMap.forEach(action: (Node) -> Unit) {
    for (i in 0 until length) {
        action(item(i))
    }
}