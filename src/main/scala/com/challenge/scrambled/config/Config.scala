package com.challenge.scrambled.config

import com.challenge.scrambled.config.Config.AppConfig
import pureconfig._
import pureconfig.generic.auto._

object Config {
  final case class AppConfig(
      dictionary: DictionaryConfig,
      anagram: AnagramConfig
  )
  final case class DictionaryConfig(
      minLength: Int,
      maxLength: Int,
      totalLength: Int
  )
  final case class AnagramConfig(
      minLength: Int
  )
}

trait Configuration {
  lazy val configuration: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
}
