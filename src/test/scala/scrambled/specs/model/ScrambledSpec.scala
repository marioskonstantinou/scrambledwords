package scrambled.specs.model

import com.challenge.scrambled.model.{Dictionary, LineWithIndex, LineWithOccurrences}
import scrambled.specs.Fakes._
import scrambled.specs.testkit.SrambledTestkit

class ScrambledSpec extends SrambledTestkit {
  "LineWithIndex reads" should {
    "return increased provided index with line" in {
      LineWithIndex.reads(fakeLines.head, 0) mustBe LineWithIndex(1, fakeLines.head)
    }
  }
  "LineWithOccurrences toString" should {
    "successfully print the overriden value" in {
      LineWithOccurrences(1, 4).toString mustBe "Case #1: 4"
    }
  }
  "Dictionary creation" should {
    "filter out provided words not meeting the required thresholds" in {
      Dictionary(fakeDictionaryInvalidWords, dictionaryConfig) mustBe Dictionary(Seq("keepme"))
    }
  }

}
