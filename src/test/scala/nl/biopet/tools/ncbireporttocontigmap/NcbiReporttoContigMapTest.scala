package nl.biopet.tools.ncbireporttocontigmap

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

class NcbiReporttoContigMapTest extends BiopetTest {
  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      NcbiReporttoContigMap.main(Array())
    }
  }
}
