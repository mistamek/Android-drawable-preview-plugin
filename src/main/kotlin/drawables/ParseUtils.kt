package drawables

import org.w3c.dom.Node
import java.awt.Color
import java.util.*

class ParseUtils {
    companion object {

        fun parseAttributeAsInt(node: Node, defaultValue: Int): Int {
            return try {
                Scanner(node.nodeValue).useDelimiter("\\D+").nextInt()
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun parseAttributeAsFloat(node: Node, defaultValue: Float): Float {
            return try {
                Scanner(node.nodeValue).useDelimiter("\\D+").nextFloat()
            } catch (e: Exception) {
                defaultValue
            }
        }

        fun parseAttributeAsColor(node: Node, defaultColor: Color?): Color? {
            return try {
                Color.decode(node.nodeValue.replace("#", "0x"))
            } catch (e: Exception) {
                defaultColor
            }
        }
    }
}