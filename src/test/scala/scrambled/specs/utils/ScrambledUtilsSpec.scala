package scrambled.specs.utils

import com.challenge.scrambled.config.CliArgs
import com.challenge.scrambled.config.Config.AppConfig
import com.challenge.scrambled.model.{InvalidCliArgumentsError, LineWithOccurrences}
import com.challenge.scrambled.utils.ScrambledUtils
import scrambled.specs.Fakes._
import scrambled.specs.testkit.SrambledTestkit

class ScrambledUtilsSpec extends SrambledTestkit {
  "ScrambledUtils canAnagram" should {
    "return true for correctly provided word" in {
      ScrambledUtils.canAnagram(fakeAnagramCorrect)(anagramConfig) mustBe true
    }
    "return false for incorrectly provided word" in {
      ScrambledUtils.canAnagram(fakeAnagramSingleIncorrect)(anagramConfig) mustBe false
    }
  }
  "ScrambledUtils anagramWord" should {
    "return a list of anagrams for correctly provided word" in {
      ScrambledUtils.anagramWord(fakeAnagramCorrect)(anagramConfig) mustBe fakeAnagramCorrectPermutations
    }
    "return a list of same provided word for words of incorrect length" in {
      ScrambledUtils.anagramWord(fakeAnagramIncorrect)(anagramConfig) mustBe Seq(
        fakeAnagramIncorrect)
    }
  }
  "ScrambledUtils dictionaryAnagrams" should {
    "provide a list of anagrams for provided dictionary" in {
      ScrambledUtils.dictionaryAnagrams(fakeDictionary)(anagramConfig) mustBe fakeDictionaryAnagrams
    }
    "provide a list of same provided words if lengths are not within thresholds" in {
      ScrambledUtils.dictionaryAnagrams(fakeDictionary.copy(words = Seq("foo")))(anagramConfig) mustBe Seq(
        "foo")
    }
  }
  "ScrambledUtils lineWithIndexFrom" should {
    "provide a list of line with index from provided list" in {
      ScrambledUtils.lineWithIndexFrom(fakeLines) mustBe fakeIndexedLines
    }
    "provide an empty list if no lines are provided" in {
      ScrambledUtils.lineWithIndexFrom(Seq()) mustBe Seq()
    }
  }
  "ScrambledUtils occurrencesFor" should {
    "find occurrences of dictionary in provided lines" in {
      ScrambledUtils.occurrencesFor(
        ScrambledUtils.lineWithIndexFrom(fakeInputSentences),
        fakeDictionary)(anagramConfig) mustBe Seq(
        LineWithOccurrences(1, 4),
        LineWithOccurrences(2, 2),
        LineWithOccurrences(3, 0))
    }
  }

  "ScrambledUtils extractFromInput" should {
    "read files and extract occurrences" in {
      ScrambledUtils
        .extractFromInput(
          CliArgs("/dictionary.txt", "/input.txt"),
          AppConfig(dictionaryConfig, anagramConfig))
        .right
        .value mustBe Seq(
        LineWithOccurrences(1, 3),
        LineWithOccurrences(2, 2),
        LineWithOccurrences(3, 1))
    }
    "attempt to read files and return error" in {
      ScrambledUtils
        .extractFromInput(
          CliArgs("/notfound.txt", "/notfoundeither.txt"),
          AppConfig(dictionaryConfig, anagramConfig))
        .left
        .value mustBe InvalidCliArgumentsError
    }
  }
}
