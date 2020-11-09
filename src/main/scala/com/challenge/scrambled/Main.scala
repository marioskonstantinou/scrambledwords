package com.challenge.scrambled

import com.challenge.scrambled.config.CliArgs.optParser
import com.challenge.scrambled.config.{CliArgs, Configuration, Loggie}
import com.challenge.scrambled.utils.ScrambledUtils.extractFromInput

object Main extends App with Loggie with Configuration {
  optParser.parse(args, CliArgs()) match {
    case Some(arguments) =>
      extractFromInput(arguments, configuration) match {
        case Right(occ)  => occ.foreach(x => logger.info(x.toString))
        case Left(value) => logger.error(value.getMessage)
      }
    case None =>
      sys.exit(1)
  }
}
