package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import searcher.Alphabet;
import searcher.Searcher;
import shiftlist.LetterList;
import shiftlist.ShiftList;

/**
 * <p>
 * This executes the suite of tests for 1 chromosome and 1 searcher type. All 10
 * subsequences are tested a total of 5 times (assuming the subsequences.txt
 * file still contains 10 subsequences, and that the runtimes variable below
 * still reads 5).
 * </p>
 * 
 * <p>
 * Please note that as this is a research project, there is limited error
 * checking, especially for things such as the existence of subdirectories and
 * files needed for input and output, as well as limited error checking on the
 * string args passed in. If you are using this outside of the original research
 * project, use at your own risk!
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Checker
{
	public static final int runtimes = 5;
	public static final String subsequencesPath = "CLDNA/subsequences.txt";
	public static final String shiftListFileName = "shiftLists/shiftList";

	public static LetterList[] createLetterList(String distinguisher)
			throws IOException
	{
		File input = null;
		Scanner reader = null;
		LetterList[] lists = new LetterList[Alphabet.getSize()];

		for (int i = 0; i < Alphabet.getSize(); i++)
		{
			input = new File(shiftListFileName + Alphabet.getLetter(i)
					+ distinguisher + ".txt");
			reader = new Scanner(input);
			lists[i] = LetterList.createLetterList(Alphabet.getLetter(i),
					reader);
			reader.close();
		}

		return lists;
	}

	public static String createInputFileName(String distinguisher)
	{
		return "CLDNA/CLDNAchr" + distinguisher + ".txt";
	}

	public static String createOutputFileName(String type, String distinguisher)
	{
		return type + "-results/CLDNAchr" + distinguisher + "-results.csv";
	}

	public static String createFoundFileName(String type, String distinguisher)
	{
		return type + "-results/CLDNAchr" + distinguisher + "-found.csv";
	}

	public static String getSourceText(String distinguisher)
			throws FileNotFoundException
	{
		File inputFile = new File(createInputFileName(distinguisher));
		String text = "";

		if (inputFile.exists())
		{
			System.out.println(
					inputFile.getName() + " exists and would be read!");
		} else
		{
			System.out.println(
					inputFile.getName() + " file seems to be missing...");
		}

		System.out.println("Reading source text file");
		Scanner reader = new Scanner(inputFile);

		while (reader.hasNext())
		{
			text += reader.next();
		}

		reader.close();
		return text;
	}

	public static ArrayList<String> getSubsequences()
			throws FileNotFoundException
	{
		File subsequencesFile = new File(subsequencesPath);
		String keeper = "";

		if (subsequencesFile.exists())
		{
			System.out.println(
					subsequencesFile.getName() + " exists and would be read!");
		} else
		{
			System.out.println(subsequencesFile.getName()
					+ " file seems to be missing...");
		}

		System.out.println("Reading subsequences file");

		Scanner reader = new Scanner(subsequencesFile);
		ArrayList<String> subsequences = new ArrayList<String>();

		while (reader.hasNextLine())
		{
			keeper = reader.nextLine();
			subsequences.add(keeper);
		}

		reader.close();

		return subsequences;
	}

	public static void main(String[] args) throws IOException
	{
		/*
		 * This reads arguments in from the command line. Warning: limited error
		 * checking!
		 */
		String distinguisher = "1";
		SearcherType type = SearcherType.defaultType;

		if (args != null && args.length > 0)
		{
			distinguisher = args[0];
			if (args.length > 1)
			{
				type = SearcherType.valueOf(args[1]);
			}
		}

		System.out.println("Testing with " + type.toString());

		// This gets the source text from file.
		String text = getSourceText(distinguisher);
		// This gets the list of subsequences from file.
		ArrayList<String> subsequences = getSubsequences();

		/*
		 * If this searcher type uses a shift list, this loads the pre-built
		 * shift list from file for the algorithm to use.
		 */
		ShiftList shiftList = null;
		if (type.usesShiftList())
		{
			System.out.println("Building Shift List");
			shiftList = new ShiftList(text, createLetterList(distinguisher));
		}

		/*
		 * This instantiates the output files and gets everything ready to start
		 */
		System.out.println("Running Tests now");

		File outputFile = new File(
				createOutputFileName(type.toString(), distinguisher));

		if (!outputFile.getParentFile().exists())
		{
			outputFile.mkdirs();
		}

		if (outputFile.exists())
		{
			outputFile.delete();
		}

		outputFile.createNewFile();

		File foundFile = new File(
				createFoundFileName(type.toString(), distinguisher));

		if (!foundFile.getParentFile().exists())
		{
			foundFile.mkdirs();
		}
		if (foundFile.exists())
		{
			foundFile.delete();
		}

		foundFile.createNewFile();

		FileWriter outputFileWriter = new FileWriter(foundFile);
		PrintWriter printWriter = new PrintWriter(outputFileWriter);
		String keeper = "";

		Searcher searcher = null;
		String substring = "";
		long start, end;
		ArrayList<Integer> results = null;

		/*
		 * This runs the full suite of tests for all subsequences the specified
		 * number of times.
		 */
		for (int i = 0; i < runtimes; i++)
		{
			System.out.println("Starting Test " + i);

			/*
			 * This iterates through the list of subsequences for 1 full suite
			 * of tests.
			 */
			for (int j = 0; j < subsequences.size(); j++)
			{
				System.out.println("Testing Subsequence " + j);
				substring = subsequences.get(j);

				// This builds the output string that will be written to file.
				keeper += "Test Run " + i + "," + "Subsequence " + j + ",";

				/*
				 * If this searcher type uses a shift list, the pre-built shift
				 * list is passed into the constructor. Otherwise, the regular
				 * instantiation method is called.
				 */
				if (type.usesShiftList())
				{
					searcher = type.createSearcher(shiftList, substring);
				} else
				{
					searcher = type.createSearcher(text, substring);
				}

				// This runs 1 test in the test suite and measures execution
				// time in nanoseconds.
				start = System.nanoTime();
				results = searcher.search();
				end = System.nanoTime();

				keeper += "Time: " + (end - start) + " nanoseconds,"
						+ "Comparisons: " + searcher.getComparisons() + "\n";

				/*
				 * For the first set of tests, the results set is output to file
				 * for error checking.
				 */
				if (i == 0)
				{
					System.out.println("Writing results data");
					printWriter.print("Testing Subsequence " + j + ",");
					for (Integer k : results)
					{
						printWriter.print(k + ",");
					}
					printWriter.println();
				}
			}

			// This closes the "found" results output file.
			printWriter.close();
			outputFileWriter.close();
		}

		/*
		 * This saves the experimental test data for this suite of tests to
		 * file.
		 */
		System.out.println("Creating results file");
		/*
		 * This deletes an old output file if it exists, to prevent good data
		 * being appended after obsolete data.
		 */
		if (outputFile.exists())
		{
			outputFile.delete();
		}
		outputFile.createNewFile();
		outputFileWriter = new FileWriter(outputFile, true);
		printWriter = new PrintWriter(outputFileWriter);

		System.out.println("Writing results file");
		printWriter.println(keeper);
		printWriter.close();
		System.out.println("Done");
	}

}
