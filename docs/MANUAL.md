# Manual
NcbiReportToContigMap needs a NCBI assembly report, an output file, and the name column that should be used from the report.
All columns in the report can be used but this are the most common field to choose from:
 - 'Sequence-Name': Name of the contig within the assembly
 - 'UCSC-style-name': Name of the contig used by UCSC ( like hg19 )
 - 'RefSeq-Accn': Unique name of the contig at RefSeq (default for NCBI)
 
Optionally other keys in the report can be used as well.

Example:
```bash
java -jar NcbiReportToContigMap-version --jar \
-a ncbi_assembly_report.txt \
-o contig_map \
--nameHeader Refseq-Accn \
--names key1 key2
```