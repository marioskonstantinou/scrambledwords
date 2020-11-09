package com.challenge.scrambled.config

import scala.io.Source
import scala.util.{Failure, Success, Try}

trait IOUtils {

  /**
    * Reads and returns file line from provided path (either from resource or provided url)
    *
    * @param path provided path of file
    * @return `Seq` of
    */
  def readLinesFrom(path: String): Seq[String] = {
    val source = Try {
      Source.fromFile(path)
    }.getOrElse(Source.fromURL(getClass.getResource(path)))
    Try {
      val fileLines = source.getLines().toSeq
      source.close()
      fileLines
    } match {
      case Success(value) =>
        source.close()
        value
      case Failure(e) =>
        source.close()
        throw e
    }
  }

}
