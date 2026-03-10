package ru.brl.puhakan
package config

import java.io.{BufferedReader, File, FileInputStream, FileReader, InputStream, Reader}
import java.net.URI
import java.nio.file.Path
import scala.annotation.tailrec
import scala.io.Source
import scala.util.{Failure, Success, Try, Using}

/** Process config file.
 *
 */
class BootstrapConfig(private val configFile: URI) {

  loadConfig()

  private def loadConfig(): Unit = {
    val rootNode: ConfNode = ConfNode()
    Using(Source.fromURI(configFile)) { reader =>
      val iter: Iterator[String] = reader.getLines().filter(!_.isBlank).iterator
      val configRootNode: ConfNode = buildTree(ConfNode(), iter, 0)
    }.toEither match {
      case Left(ex) =>
        println("Comprehensive logging needed. Exception occured")
        throw RuntimeException(ex)
      case Right(_) =>
    }
  }

  @tailrec
  private def buildTree(confNode: ConfNode, iterator: Iterator[String], previousPad: Int): ConfNode = {
    val currentLine: String = iterator.next()
    
    val colonIndex: Int = currentLine.indexOf(':')
    require(colonIndex > -1, s"Invalid line $currentLine")

    val (paramName, paramValue): (String, String) = currentLine.splitAt(colonIndex)
    
    require(paramName != null, s"Invalid line: $currentLine")

    if (!iterator.hasNext) {
      require(!paramValue.isBlank, s"Invalid line: $currentLine")
      ConfNode(paramName.trim, paramValue, Array())
    } else {
      if (!paramValue.isBlank) {
        ConfNode(paramName, Array(paramValue.tail.trim))
        confNode.append()
      } else {
        val newNode: ConfNode = ConfNode(paramName, Array())
        
      }
//      buildTree()
    }

  }
}
