package validityCheck;

import java.util.*;
import searcher.Searcher;

public class ValidityCheck extends Searcher
{
	public ValidityCheck(String text, String substring)
			throws NullPointerException, IndexOutOfBoundsException
	{
		super(text, substring);
	}

	public static ArrayList<Integer> staticSearch(String text, String substring)
	{
		ArrayList<Integer> searchResults = new ArrayList<Integer>();
		int i = -1;

		do
		{
			i = text.indexOf(substring, i + 1);
			if (i > -1 && i < text.length())
			{
				searchResults.add(i);
			}
		} while (i > -1 && i < text.length());

		return searchResults;
	}

	public ArrayList<Integer> search(String text, String substring)
	{
		return this.search(text, substring);
	}
}
