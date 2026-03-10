package ru.brl.puhakan
package config

import scala.collection.mutable.ArrayBuffer

/** Node in a yml-config tree.
 *
 * @author Roman Beschastnykh
 * @since 0.0.0
 */
case class ConfNode(private val name: String = null.asInstanceOf[String],
                    private val value: String = "",                   
                    private val children: Option[ArrayBuffer[ConfNode]] = Some(ArrayBuffer())) {
}
