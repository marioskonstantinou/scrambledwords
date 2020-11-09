package com.challenge.scrambled.utils

import com.challenge.scrambled.config.Config.{AnagramConfig, AppConfig}
import com.challenge.scrambled.config.{CliArgs, IOUtils, Loggie}
import com.challenge.scrambled.model.{
  ApplicationError,
  Dictionary,
  InvalidCliArgumentsError,
  LineWithIndex,
  LineWithOccurrences,
  ScrambledInput
}
import com.challenge.scrambled.model.Scrambled.{DictionaryWord, Line, Occurrences}

import scala.util.{Failure, Success, Try}

object ScrambledUtils extends Loggie with IOUtils {

  /**
    * For provided dictionary, generate the corresponding anagrams
    *
    * @param dictionary provided dictionary
    * @param anagramConfig anagram configuration found in application.conf
    * @return
    */
  def dictionaryAnagrams(dictionary: Dictionary)(
      anagramConfig: AnagramConfig): Seq[DictionaryWord] = {
    dictionary.words.foldLeft(Seq.empty[DictionaryWord]) { (z, i) =>
      z ++ anagramWord(i)(anagramConfig)
    }
  }

  /**
    * Generates a list or occurrences of each dictionary word found in each sentence
    *
    * @param inputSentences lines from input arg
    * @param dictionary words (dictionary) extracted from provided dictionary arg
    * @param anagramConfig anagram configuration found in application.conf
    * @return
    */
  def occurrencesFor(inputSentences: Seq[LineWithIndex], dictionary: Dictionary)(
      anagramConfig: AnagramConfig): Seq[LineWithOccurrences] = {
    inputSentences.map { s =>
      val occ: Occurrences = dictionaryAnagrams(dictionary)(anagramConfig).foldLeft(0) { (z, i) =>
        s.line.contains(i) match {
          case true => z + 1
          case _    => z
        }
      }
      LineWithOccurrences(s.index, occ)
    }
  }

  /**
    * Rule to check if length of provided dictionary word exceeds configured min length
    *
    * @param s
    * @return
    */
  def canAnagram(s: DictionaryWord)(anagramConfig: AnagramConfig): Boolean =
    s.length > anagramConfig.minLength

  /**
    * {{{
    *   Anagrams provided dictionary word
    *
    *   Rules:
    *      1. First and last letter of provided word remain as is
    *      2. Anagram words only if length is greater than the configured length (anagramConfig.minLength)
    * }}}
    *
    * @param word provided word to anagram
    * @return a Seq of anagrams of provided word
    */
  def anagramWord(word: DictionaryWord)(anagramConfig: AnagramConfig): Seq[DictionaryWord] = {
    logger.debug(s"Attempting to anagram $word")
    def anagram(w: DictionaryWord): Seq[String] = {
      val (firstLetter, lastLetter) = (word.head, word.last)
      w.substring(1, w.length - 1).toSeq.permutations.map(p => s"$firstLetter$p$lastLetter").toSeq
    }

    val wordAnagram = canAnagram(word)(anagramConfig) match {
      case true  => anagram(word)
      case false => Seq(word)
    }
    logger.debug(s"$word anagrams (${wordAnagram.size}) (${wordAnagram})")
    wordAnagram
  }

  /**
    * Converts provided Seq of `Lines` in a Seq of `LineWithIndex` (index, element)
    *
    * @param lines
    * @return
    */
  def lineWithIndexFrom(lines: Seq[Line]): Seq[LineWithIndex] =
    lines.zipWithIndex.map(LineWithIndex.reads)

  /**
    * {{{
    *     1. Attempts to read contents of files
    *     2. Validates dictionary
    *     3. Extracts occurrences of each word in dictionary in every sentence
    * }}}
    *
    * @param cliArgs provided Cli Arguments
    * @param appConfig Application configuration based on `application.conf`
    *
    * @return ApplicationError for failed processing or Seq[LineWithOccurrences]
    */
  def extractFromInput(cliArgs: CliArgs,
                       appConfig: AppConfig): Either[ApplicationError, Seq[LineWithOccurrences]] = {
    Try {
      val inputDictionary: Seq[DictionaryWord] = readLinesFrom(cliArgs.dictionary)
      val inputSentences: Seq[LineWithIndex] =
        ScrambledUtils.lineWithIndexFrom(readLinesFrom(cliArgs.input))
      ScrambledInput(inputDictionary, inputSentences)
    } match {
      case Failure(_) => Left(InvalidCliArgumentsError)
      case Success(si) =>
        val dictionary: Dictionary =
          Dictionary(si.dictionary, appConfig.dictionary)

        DictionaryUtils.validate(dictionary)(appConfig.dictionary) match {
          case Right(validatedDictionary) =>
            Right(
              ScrambledUtils
                .occurrencesFor(si.input, validatedDictionary)(appConfig.anagram))
          case Left(e) =>
            Left(e)
        }
    }

  }

}
