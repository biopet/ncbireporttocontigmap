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

import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}

class ArgsParser(toolCommand: ToolCommand[Args])
    extends AbstractOptParser[Args](toolCommand) {
  opt[File]('a', "assembly_report") required () valueName "<file>" action {
    (x, c) =>
      c.copy(assemblyReport = x)
  } text "Assembly report from NCBI"
  opt[File]('o', "output") required () valueName "<file>" action { (x, c) =>
    c.copy(outputFile = x)
  } text "output contig map"
  opt[String]("nameHeader") required () valueName "<string>" action { (x, c) =>
    c.copy(contigNameHeader = x)
  } text
    """
      | What column to use from the NCBI report for the name of the contigs.
      | All columns in the report can be used but this are the most common field to choose from:
      | - 'Sequence-Name': Name of the contig within the assembly
      | - 'UCSC-style-name': Name of the contig used by UCSC ( like hg19 )
      | - 'RefSeq-Accn': Unique name of the contig at RefSeq (default for NCBI)""".stripMargin
  opt[String]("names") unbounded () action { (x, c) =>
    c.copy(names = x.split(",").toList)
  } text "Keys of the report to use in contig map"
}
