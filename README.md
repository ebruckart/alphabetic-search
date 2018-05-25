# alphabetic-search
Source code for a senior research project testing exact single pattern matches over DNA sequences.  New searching algorithms are presented that pre-process the source text and use that information to speed up the search.

24 May 2018
Uploaded the raw source code for my senior research project.  To run quick tests using this code, classes in the junit package can be used to demonstrate functionality without using outside resources.  Please note that because this was a research experiment, this has limited error checking.

The experiment classes all use hard-coded file paths and names of source texts and subsequences that were used for the testing phase, and these files have not been included with these source code files.  If someone wishes to recreate these experiments, they can do so by downloading a source genome from the National Center for Biotechnology Information.  When this experiment was run, the source DNA sequences took roughly 2GB of space on the local machine.  This project originally used dog DNA as the source chromosomes, which are available here:

ftp://ftp.ncbi.nih.gov/genomes/Canis_lupus_familiaris/

You will need to pre-process the source texts by stripping out the header and any instances of characters besides A, C, G, and T, as well as rename the files to the expected name.  The FileScrubber class is included in the experiment package that will handle this pre-processing step.

To generate a random set of subsequences to test with, you can use the SubstringGenerator class included in the experiment package.

In order to run the classes that require shift tables (the categoric searcher and the traditional searchers), the shift tables will need to be created and stored as a pre-processing step.  This requires a lot of space on your machine.  When running the tests, 20 GB was needed to store the shift tables for the full source genome.  The ShiftTableGenerator class in the experiment package can be used to create the needed shift tables.

After all the pre-processing steps have been completed, you can use the Checker class in the experiment package to run the experiment.  Allocating at least 1.5 GB of RAM to the JVM is recommended for running experiments using searchers that do not require a shift table, and at least 3.5 GB of RAM allocated to the JVM is recommended for experiments using searchers that DO require a shift table.  This experiment was originally run allocating 3.5 GB of RAM to the JVM for all tests.

The Checker class expects 2 arguments when running.  Assuming your DNA source texts use the same structure, the first argument should be the distinguisher for a particular chromosome, a number between 1 and 38 in the case of the dog DNA.  This does not strictly require number distinguishers, as "X", "MT", and "Unplaced" are acceptable distinguishers as well.  The second expected argument is what type of searcher to use for this experiment.  Spelling and capitalization should be an exact match to one of the options.  The accepted options at this point are:

validity
horspools
boyermoore
traditional
categorical

The bash script that managed running the experiment on all chromosomes in the genome for a particular searcher type has been included for reference.  Please note the jar file will need to be compiled locally from the source before attempting to run this script.

Thank you for looking at my code, and I hope you have a nice day. :)
-Emily