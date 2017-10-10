package nl.biopet.tools.ncbireporttocontigmap

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

object NcbiReporttoContigMapTest extends BiopetTest {
  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      ToolTemplate.main(Array())
    }
  }
}
