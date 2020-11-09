package com.challenge.scrambled.config

import scopt.OptionParser

case class CliArgs(dictionary: String = "", input: String = "")

object CliArgs {
  val optParser: OptionParser[CliArgs] = new scopt.OptionParser[CliArgs]("Scrambled Words") {
    head("Scrambled Words Cli Args")

    override def errorOnUnknownArgument = false

    override def showUsageOnError: Option[Boolean] = Some(true)

    opt[String]("dictionary")
      .required()
      .valueName("dictionary")
      .action { (input, config) =>
        config.copy(dictionary = input)
      }
      .text("Dictionary path")

    opt[String]("input")
      .required()
      .valueName("input")
      .action { (input, config) =>
        config.copy(input = input)
      }
      .text("Input path")

  }
}
