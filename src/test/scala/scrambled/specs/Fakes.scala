package scrambled.specs

import com.challenge.scrambled.config.Config.{AnagramConfig, DictionaryConfig}
import com.challenge.scrambled.model.{Dictionary, LineWithIndex}
import com.challenge.scrambled.model.Scrambled.{DictionaryWord, Line}

object Fakes {
  val anagramConfig: AnagramConfig = AnagramConfig(3)
  val dictionaryConfig: DictionaryConfig = DictionaryConfig(
    minLength = 2,
    maxLength = 105,
    totalLength = 105
  )
  val fakeDictionaryWords: Seq[DictionaryWord] = Seq(
    "axpaj",
    "apxaj",
    "dnrbt",
    "pjxdn",
    "abd"
  )
  val fakeDictionaryAnagrams: Seq[DictionaryWord] = Seq(
    "axpaj",
    "axapj",
    "apxaj",
    "apaxj",
    "aaxpj",
    "aapxj",
    "apxaj",
    "apaxj",
    "axpaj",
    "axapj",
    "aapxj",
    "aaxpj",
    "dnrbt",
    "dnbrt",
    "drnbt",
    "drbnt",
    "dbnrt",
    "dbrnt",
    "pjxdn",
    "pjdxn",
    "pxjdn",
    "pxdjn",
    "pdjxn",
    "pdxjn",
    "abd"
  )
  val fakeDictionaryLongWords: Seq[DictionaryWord] = Seq(
    "axpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpajaxpaja",
    "apxaj",
    "dnrbt",
    "pjxdn",
    "apjxdnpjxdnbdaa"
  )
  val fakeDictionaryInvalidWords: Seq[DictionaryWord] = Seq(
    "dontkeepthisdontkeepthisdontkeepthisdontkeepthisdontkeepthisdontkeepthisdontkeepthisdontkeepthisdontkeepthis",
    "keepme",
    "a"
  )
  val fakeDictionary: Dictionary = Dictionary(fakeDictionaryWords, dictionaryConfig)
  val fakeAnagramCorrect: DictionaryWord = "abcde"
  val fakeAnagramCorrectPermutations = Seq("abcde", "abdce", "acbde", "acdbe", "adbce", "adcbe")
  val fakeAnagramSingleIncorrect: DictionaryWord = "a"
  val fakeAnagramIncorrect: DictionaryWord = "abc"
  val fakeLines: Seq[Line] = Seq("firstline", "secondline", "thirdline")
  val fakeIndexedLines: Seq[LineWithIndex] = Seq(
    LineWithIndex(1, "firstline"),
    LineWithIndex(2, "secondline"),
    LineWithIndex(3, "thirdline"))

  val fakeInputSentences = Seq(
    "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt",
    "nrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt",
    "btvldptfzbbdbbzxtndrvjbl"
  )
}
