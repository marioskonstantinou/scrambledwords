package scrambled.specs.utils

import com.challenge.scrambled.config.IOUtils
import scrambled.specs.testkit.SrambledTestkit

class IOUtilsSpec extends SrambledTestkit with IOUtils {
  "IOUtils readFrom" should {
    "extract lines from provided file path" in {
      readLinesFrom("/iotest") mustBe Seq("line1", "line2")
    }
  }
}
