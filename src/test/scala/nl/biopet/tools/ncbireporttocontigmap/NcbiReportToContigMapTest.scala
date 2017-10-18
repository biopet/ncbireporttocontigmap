package nl.biopet.tools.ncbireporttocontigmap

import java.io.File

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

import scala.io.Source

class NcbiReportToContigMapTest extends BiopetTest {
  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      NcbiReportToContigMap.main(Array())
    }
  }

  @Test
  def test(): Unit = {
    val report = new File(resourcePath("/GCF_000844745.1.report"))
    val output = File.createTempFile("test.", ".tsv")
    output.deleteOnExit()
    NcbiReportToContigMap.main(
      Array("-a",
        report.getAbsolutePath,
        "-o",
        output.getAbsolutePath,
        "--nameHeader",
        "Sequence-Name"))

    Source.fromFile(output).getLines().toList shouldBe List(
      "#Name_in_fasta\tAlternative_names",
      "Unknown\tNC_003403.1"
    )
  }

}
