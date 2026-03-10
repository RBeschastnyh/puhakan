package ru.brl.puhakan
package config

import java.net.URI
import scala.annotation.tailrec
import scala.io.Source
import scala.util.Using
import scala.collection.BufferedIterator
import scala.collection.mutable.ArrayBuffer

/** Process config file.
 *
 */
class BootstrapConfig(private val configFile: URI) {

  loadConfig()

  private def loadConfig(): Unit = {
    val rootNode: ConfNode = Using(Source.fromURI(configFile)) { reader =>
      val iter: BufferedIterator[String] = reader.getLines().filter(!_.isBlank).iterator.buffered

      @tailrec
      def buildTree(confNode: ConfNode): ConfNode = {
        if (!iter.hasNext) {
          confNode
        } else {
          val currentLine: String = iter.next()

          val colonIndex: Int = currentLine.indexOf(':')
          require(colonIndex > -1, s"Invalid line $currentLine")

          val (paramName, paramValue): (String, String) = currentLine.splitAt(colonIndex)

          val (value, nextNode): (String, Option[ArrayBuffer[ConfNode]]) = if (!iter.hasNext || compareWhiteSpaces(currentLine, iter.head) <= 0) {
            require(paramValue != null, s"Invalid line $currentLine")
            (paramValue.tail.trim, None)
          } else {
            (null, Option(ArrayBuffer[ConfNode]()))
          }

          val node: ConfNode = ConfNode(paramName.trim, value, nextNode)

          confNode._3 match {
            case Some(ls) => ls += node
            case None =>
          }

          node._3 match {
            case Some(_) => buildTree(node)
            case None => buildTree(confNode)
          }
        }

      }

      buildTree(ConfNode())
    }.toEither match {
      case Left(ex) =>
        println("Comprehensive logging needed. Exception occurred")
        throw RuntimeException(ex)
      case Right(va) => va
    }
  }

  private def compareWhiteSpaces(cur: String, next: String): Int = {
    val currentLineWs: Int = cur.length - cur.dropWhile(c => c == ' ').length
    val nextLineWs: Int = next.length - next.dropWhile(c => c == ' ').length

    nextLineWs - currentLineWs
  }
}
