package nl.biopet.tools.ncbireporttocontigmap

import java.io.File

import nl.biopet.utils.tool.AbstractOptParser

class ArgsParser(toolCommand: ToolCommand[Args])
    extends AbstractOptParser[Args](toolCommand) {
  opt[File]('a', "assembly_report") required () unbounded () valueName "<file>" action {
    (x, c) =>
      c.copy(assemblyReport = x)
  } text "Assembly report from NCBI"
  opt[File]('o', "output") required () unbounded () valueName "<file>" action {
    (x, c) =>
      c.copy(outputFile = x)
  } text "output contig map"
  opt[String]("nameHeader") required () unbounded () valueName "<string>" action {
    (x, c) =>
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
