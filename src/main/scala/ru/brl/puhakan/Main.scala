package ru.brl.puhakan

import java.nio.file.{Path, Paths}

import util.BuildConstants
import config.BootstrapConfig

object Main {

  def main(args: Array[String]): Unit = {
    val currentDirectoryPath: Path = Paths.get("")

    println(s"Current directory is: ${currentDirectoryPath.toUri}")

    val fileConf: BootstrapConfig = new BootstrapConfig(
      currentDirectoryPath.toUri.resolve(BuildConstants.CONFIG_FILE_NAME)
    )
  }
}
