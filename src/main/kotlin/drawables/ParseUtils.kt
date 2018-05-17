package drawables

import org.w3c.dom.Node
import java.util.*

class ParseUtils {
    companion object {

        fun parseAttributeAsInt(node: Node): Int {
            return try {
                Scanner(node.nodeValue).useDelimiter("\\D+").nextInt()
            } catch (e: Exception) {
                0
            }
        }
    }
}