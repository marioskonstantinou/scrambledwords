package com.challenge.scrambled.model

import com.challenge.scrambled.config.Config.DictionaryConfig
import com.challenge.scrambled.config.Loggie
import com.challenge.scrambled.model.Scrambled.{DictionaryWord, Line, LineIndex, Occurrences}

object Scrambled {
  type DictionaryWord = String
  type Line = String
  type Occurrences = Int
  type LineIndex = Int

}

case class Dictionary(words: Seq[DictionaryWord]) {}

object Dictionary extends Loggie {
  def apply(words: Seq[DictionaryWord], dictionaryConfig: DictionaryConfig): Dictionary = {
    val dictionary = Dictionary(
      words
        .filter(w => {
          val wordLength = w.length
          wordLength >= dictionaryConfig.minLength && wordLength <= dictionaryConfig.maxLength
        })
        .distinct)
    logger.info(s"Filtered dictionary $dictionary")
    dictionary
  }
}

case class LineWithIndex(index: LineIndex, line: String)

object LineWithIndex {
  def reads(x: (Line, LineIndex)): LineWithIndex = LineWithIndex(x._2 + 1, x._1.toLowerCase)
}

case class LineWithOccurrences(index: LineIndex, occurrences: Occurrences) {
  override def toString: Line = s"Case #$index: $occurrences"
}

case class ScrambledInput(dictionary: Seq[DictionaryWord], input: Seq[LineWithIndex])
