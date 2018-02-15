/*
 * Copyright (c) 2017 Biopet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.biopet.tools.ncbireporttocontigmap

import java.io.File

import nl.biopet.utils.test.tools.ToolTest
import org.testng.annotations.Test

import scala.io.Source

class NcbiReportToContigMapTest extends ToolTest[Args] {
  def toolCommand: NcbiReportToContigMap.type = NcbiReportToContigMap
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
