package nl.biopet.tools.ncbireporttocontigmap

import java.io.File

case class Args(assemblyReport: File = null,
                outputFile: File = null,
                reportFile: Option[File] = None,
                contigNameHeader: String = null,
                names: List[String] =
                List("Sequence-Name", "UCSC-style-name", "GenBank-Accn", "RefSeq-Accn"))
