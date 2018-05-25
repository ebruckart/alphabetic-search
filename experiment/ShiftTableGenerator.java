package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import searcher.Alphabet;

public class ShiftTableGenerator
{
	public static final String outputFileName = "shiftLists/shiftList";
	public static final String inputFileName = "CLDNA/CLDNAchr";
	public static final String suffix = ".txt";

	public static String createInputFileName(String distinguisher)
	{
		return inputFileName + distinguisher + suffix;
	}

	public static String createOutputFileName(String distinguisher, int i)
	{
		return outputFileName + Alphabet.getLetter(i) + distinguisher + suffix;
	}

	public static void main(String[] args) throws IOException
	{
		String distinguisher = "1";
		if (args != null && args.length > 0)
		{
			distinguisher = args[0];
		}

		File inputFile = new File(createInputFileName(distinguisher));
		Scanner reader = new Scanner(inputFile);
		String text = "";

		File[] outputFile = new File[Alphabet.getSize()];
		FileWriter[] outputFileWriter = new FileWriter[Alphabet.getSize()];
		PrintWriter[] printWriter = new PrintWriter[Alphabet.getSize()];

		for (int i = 0; i < Alphabet.getSize(); i++)
		{
			outputFile[i] = new File(createOutputFileName(distinguisher, i));
			outputFile[i].createNewFile();

			outputFileWriter[i] = new FileWriter(outputFile[i], true);
			printWriter[i] = new PrintWriter(outputFileWriter[i]);
		}

		int iterator = 0;

		while (reader.hasNext())
		{
			text = reader.next();

			for (int j = 0; j < text.length(); j++)
			{
				printWriter[Alphabet.valueOf(text.substring(j, j + 1))
						.getPosition()].println(iterator);
				iterator++;
			}
		}

		reader.close();

		for (int i = 0; i < Alphabet.getSize(); i++)
		{
			printWriter[i].close();
			outputFileWriter[i].close();
		}

		System.out.println("Done");
	}
}
