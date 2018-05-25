package validityCheck;

import java.util.*;

public class QuickTest
{
	private static String text;
	private static String substring;

	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			setFromConsole();
		}

		else
		{
			setText("Hello World!");
			setSubstring("ello");
		}

		ArrayList<Integer> searchResults = ValidityCheck.staticSearch(text,
				substring);

		System.out.println("text: " + text + "\nsubstring: " + substring
				+ "\nsearch results: " + searchResults);
	}

	public static void setFromConsole()
	{
		Scanner scanner = new Scanner(System.in);
		boolean isSubstringShorter = false;

		while (!isSubstringShorter)
		{
			System.out.println("Please input a text to search.");

			setText(scanner.nextLine());

			System.out.println(
					"Please input a substring to search the text with.");

			setSubstring(scanner.nextLine());

			if (text.length() < substring.length())
			{
				System.out.println(
						"Error: Substring is longer than Source Text. Please try again.");
			}

			else
			{
				isSubstringShorter = true;
			}
		}

		scanner.close();
	}

	public static void setSubstring(String input)
	{
		// Method for inputting the substring text
		substring = input;
	}

	public static void setText(String input)
	{
		// Method for inputting the source text
		text = input;
	}
}