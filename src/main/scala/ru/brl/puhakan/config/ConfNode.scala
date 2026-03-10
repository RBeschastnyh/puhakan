package ru.brl.puhakan
package config

/** Node in a yml-config tree.
 *
 * @author Roman Beschastnykh
 * @since 0.0.0
 */
case class ConfNode(private val name: String = null.asInstanceOf[String],
                    private val value: Any,                   
                    private val children: Array[ConfNode] = Array()) {
}
