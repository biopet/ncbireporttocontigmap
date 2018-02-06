organization := "com.github.biopet"
organizationName := "Sequencing Analysis Support Core - Leiden University Medical Center"

startYear := Some(2017)

name := "NcbiReportToContigMap"
biopetUrlName := "ncbireporttocontigmap"

biopetIsTool := true

mainClass in assembly := Some("nl.biopet.tools.ncbireporttocontigmap.NcbiReportToContigMap")

developers := List(
  Developer(id="ffinfo", name="Peter van 't Hof", email="pjrvanthof@gmail.com", url=url("https://github.com/ffinfo"))
)

scalaVersion := "2.11.11"

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.3-SNAPSHOT" changing()
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.2-SNAPSHOT" % Test changing()