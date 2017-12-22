package nl.biopet.tools.ncbireporttocontigmap

import java.io.PrintWriter

import nl.biopet.utils.tool.ToolCommand

import scala.io.Source

object NcbiReportToContigMap extends ToolCommand[Args] {
  def emptyArgs: Args = Args()
  def argsParser = new ArgsParser(this)
  def main(args: Array[String]): Unit = {
    val cmdArgs = cmdArrayToArgs(args)

    logger.info("Start")

    logger.info(s"Reading ${cmdArgs.assemblyReport}")
    val reader = Source.fromFile(cmdArgs.assemblyReport)
    val assamblyReport = reader.getLines().toList
    reader.close()
    cmdArgs.reportFile.foreach { file =>
      val writer = new PrintWriter(file)
      assamblyReport.foreach(writer.println)
      writer.close()
    }

    val headers = assamblyReport
      .filter(_.startsWith("#"))
      .last
      .stripPrefix("# ")
      .split("\t")
      .zipWithIndex
      .toMap

    val altNameIds =
      cmdArgs.names.filter(_ != cmdArgs.contigNameHeader).map(headers)
    val nameId = headers(cmdArgs.contigNameHeader)

    val writer = new PrintWriter(cmdArgs.outputFile)
    writer.println("#Name_in_fasta\tAlternative_names")
    for (line <- assamblyReport.filter(!_.startsWith("#"))) {
      val values = line.split("\t")
      val altNames = altNameIds.map(i => values(i)).filter(_ != "na").distinct
      writer.println(values(nameId) + "\t" + altNames.mkString(";"))
    }
    writer.close()

    logger.info("Done")
  }

  def descriptionText: String =
    """
      |This tool generates a contig map file using the information
      |from a NCBI assembly report. It has an option to select
      |which column in the NCBI report should be used.
    """.stripMargin

  def manualText: String =
    s"""
      |$toolName needs a NCBI assembly report, an output file, and the name column that should be used from the report.
      |All columns in the report can be used but these are the most common fields to choose from:
      | - 'Sequence-Name': Name of the contig within the assembly
      | - 'UCSC-style-name': Name of the contig used by UCSC ( like hg19 )
      | - 'RefSeq-Accn': Unique name of the contig at RefSeq (default for NCBI)
      |
      |Optionally other columns in the report can be added to the contig map with the `--names` flag.
    """.stripMargin

  def exampleText: String =
    s"""
       | To construct a contig map from a NCBI assembly report,
       | use the UCSC-style-name for the contigs, and include the RefSeq-Accn
       | column:
       | ${example("-a",
                   "ncbi_assembly_report.txt",
                   "-o",
                   "contig_map.tsv",
                   "--nameHeader",
                   "UCSC-style-name",
                   "--names",
                   "Refseq-Accn")}
     """.stripMargin
}
