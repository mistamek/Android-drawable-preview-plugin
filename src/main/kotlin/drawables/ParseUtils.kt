package drawables

import org.w3c.dom.Node

class ParseUtils {
    companion object {
        fun parseAttributeAsInt(node: Node): Int {
            return node.nodeValue?.toInt() ?: 0
        }
    }
}