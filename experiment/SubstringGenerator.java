package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import junit.SearcherTest;
import searcher.Alphabet;

/**
 * <p>
 * This generates a designated number of random short and long read sequences to
 * be used as substrings taken from one of the DNA files. The length of the long
 * and short reads is kept as a constant listed in Alphabet.
 * </p>
 * 
 * <p>
 * Substrings are constrained in this implementation to have instances of each
 * character in the alphabet. That means all characters in the alphabet (A, C,
 * G, and T) occur at least once in every substring.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class SubstringGenerator
{
	public static final int numGenerated = 5;
	public static final int shortRead = 10;
	public static final int longRead = 50;

	public static void main(String[] args) throws IOException
	{
		File inputFile = new File("CLDNA/CLDNAchr1.txt");
		File outputFile = new File("subsequences.txt");
		FileWriter outputFileWriter = null;
		PrintWriter printWriter = null;
		String text = "";

		Scanner reader = new Scanner(inputFile);
		outputFile.createNewFile();
		outputFileWriter = new FileWriter(outputFile, true);
		printWriter = new PrintWriter(outputFileWriter);
		int randomInt = -1;
		String check = "";
		boolean found = true;

		while (reader.hasNext())
		{
			text += reader.next();
		}

		for (int i = 0; i < numGenerated; i++)
		{
			randomInt = SearcherTest.randomInt()
					% (text.length() - shortRead - 1);
			check = text.substring(randomInt, randomInt + shortRead);
			found = true;
			int j = 0;
			while (found && j < Alphabet.getSize())
			{
				found = check.contains("" + Alphabet.getLetter(j).getLetter());
				j++;
			}
			if (found)
			{
				printWriter.println(check);
			} else
			{
				i--;
			}

		}

		for (int i = 0; i < numGenerated; i++)
		{
			randomInt = SearcherTest.randomInt()
					% (text.length() - longRead - 1);
			check = text.substring(randomInt, randomInt + longRead);
			found = true;
			int j = 0;
			while (found && j < Alphabet.getSize())
			{
				found = check.contains("" + Alphabet.getLetter(j).getLetter());
				j++;
			}
			if (found)
			{
				printWriter.println(check);
			} else
			{
				i--;
			}
		}

		reader.close();
		printWriter.close();
		outputFileWriter.close();
		System.out.println("Done");
	}

}
