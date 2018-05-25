package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import searcher.Alphabet;

/**
 * <p>
 * This class pre-processes the DNA sequence files. Specifically, this strips
 * out the header and unknown "NNNNN..."'s from the data.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class FileScrubber
{
	private static final String num = "1";

	public static void main(String[] args)
	{

		Scanner scanner = null;
		String output = "";
		String keeper = "";

		File inputFile = new File(
				"CanisLupisDNA/cfa_ref_CanFam3.1_chr" + num + ".fa");
		File outputFile = new File("CLDNA/CLDNAchr" + num + ".txt");
		FileWriter outputFileWriter = null;
		PrintWriter printWriter = null;

		if (inputFile.exists())
		{
			try
			{
				scanner = new Scanner(inputFile);
				outputFile.createNewFile();
				outputFileWriter = new FileWriter(outputFile, true);
				printWriter = new PrintWriter(outputFileWriter);
			} catch (FileNotFoundException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (scanner.hasNext())
			{
				output = scanner.next();
				keeper = "";

				for (int i = 0; i < output.length(); i++)
				{
					if (Alphabet.contains(output.charAt(i)))
					{
						keeper += output.charAt(i);
					}
				}
				printWriter.print(keeper);
			}

			scanner.close();
			printWriter.close();
			try
			{
				outputFileWriter.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Done");
		}
	}

}
