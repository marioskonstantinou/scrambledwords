package scrambled.specs.utils

import com.challenge.scrambled.model.DictionaryLengthTooLong
import com.challenge.scrambled.utils.DictionaryUtils
import scrambled.specs.Fakes._
import scrambled.specs.testkit.SrambledTestkit

class DictionaryUtilsSpec extends SrambledTestkit {
  "DictionaryUtils validate" should {
    "correctly provided dictionary" in {
      DictionaryUtils.validate(fakeDictionary)(dictionaryConfig).right.value mustBe fakeDictionary
    }
    "return application error in case total length exceeds set thresholds" in {
      DictionaryUtils
        .validate(fakeDictionary.copy(words = fakeDictionaryLongWords))(dictionaryConfig)
        .left
        .value mustBe DictionaryLengthTooLong(
        fakeDictionaryLongWords.mkString("").length,
        dictionaryConfig.totalLength)
    }
  }
}
