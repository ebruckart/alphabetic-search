package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import searcher.Alphabet;
import shiftlist.LetterList;
import shiftlist.ShiftList;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class ShiftListSizer
{
	public static final String shiftListSizesName = "shiftListSizes/shiftListSizes";
	public static final String suffix = ".csv";

	public static String createOutputFileName(String distinguisher)
	{
		return shiftListSizesName + distinguisher + suffix;
	}

	public static void main(String[] args) throws IOException
	{
		String distinguisher = "1";
		if (args != null && args.length > 0)
		{
			distinguisher = args[0];
		}

		System.out.println("Building Shift List");
		ShiftList shiftList = new ShiftList("ACGT",
				Checker.createLetterList(distinguisher));

		File shiftListSizes = new File(createOutputFileName(distinguisher));

		if (shiftListSizes.exists())
		{
			shiftListSizes.delete();
		}

		shiftListSizes.createNewFile();

		FileWriter outputFileWriter = new FileWriter(shiftListSizes);
		PrintWriter printWriter = new PrintWriter(outputFileWriter);
		LetterList list = null;

		for (int i = 0; i < shiftList.getSize(); i++)
		{
			list = shiftList.getLetterList(Alphabet.getLetter(i));
			printWriter.println(list.getLetter() + "," + list.size());

		}

		printWriter.println();
		printWriter.close();
		outputFileWriter.close();

		System.out.println("Done");
	}

}
