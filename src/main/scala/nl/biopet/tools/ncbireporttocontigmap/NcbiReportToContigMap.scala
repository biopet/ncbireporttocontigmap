package nl.biopet.tools.ncbireporttocontigmap

import java.io.PrintWriter

import nl.biopet.utils.tool.ToolCommand

import scala.io.Source

object NcbiReportToContigMap extends ToolCommand {
  def main(args: Array[String]): Unit = {
    val parser = new ArgsParser(toolName)
    val cmdArgs =
      parser.parse(args, Args()).getOrElse(throw new IllegalArgumentException)

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

    val altNameIds = cmdArgs.names.filter(_ != cmdArgs.contigNameHeader).map(headers)
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
}
