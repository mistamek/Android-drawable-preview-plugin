package drawables

import org.w3c.dom.Element
import org.w3c.dom.NodeList

inline fun NodeList.forEachAsElement(action: (Element) -> Unit): Unit {
    for (i in 0 until length) {
        val childNode = item(i)
        if (childNode is Element) {
            action(childNode)
        }
    }
}