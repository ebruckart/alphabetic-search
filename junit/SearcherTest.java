package junit;

import static org.junit.Assert.*;
import org.junit.Test;

import searcher.Alphabet;
import searcher.Searcher;

import java.util.ArrayList;
import validityCheck.ValidityCheck;

/**
 * <p>
 * This is the junit test class for Searcher. This method is inherited by all
 * the Searcher testers, so these tests are run quite a bit lol.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class SearcherTest
{

	/**
	 * <p>
	 * This converts an integer value into a String built with letters that are
	 * found in the enum Alphabet.
	 * </p>
	 * 
	 * @param numInt
	 *            - Integer to be converted into a string that is composed of
	 *            Alphabet letters.
	 * @return Returns a String composed of Alphabet letters that corresponds to
	 *         the value of the original Integer.
	 */
	public static String intToAlphabetString(Integer numInt)
	{
		String output = "";
		String numString = Integer.toString(numInt, Alphabet.getSize());

		for (int i = 0; i < numString.length(); i++)
		{
			output += Alphabet
					.getLetter(Integer.parseInt(numString.substring(i, i + 1)));
		}

		return output;
	}

	/**
	 * <p>
	 * This generates a random integer value between 0 and Integer.MAX_VALUE,
	 * not including 0.
	 * </p>
	 * 
	 * @return Returns a random Integer.
	 */
	public static Integer randomInt()
	{
		return new Integer((int) (Math.random() * Integer.MAX_VALUE));
	}

	/**
	 * <p>
	 * This method exists mainly so that classes that inherit from this class
	 * can overwrite this with their own implementations of searcher, which can
	 * still be passed into the tests here and run on other implementations of
	 * searchers.
	 * </p>
	 * 
	 * @param text
	 *            - The text this is going to be initialized with.
	 * @param substring
	 *            - The substring this is going to be initialized with.
	 * @return returns a Searcher compatible implementation to test that the
	 *         search works as expected.
	 */
	public Searcher initializeSearcher(String text, String substring)
	{
		return new Searcher(text, substring);
	}

	/**
	 * <p>
	 * This verifies that the constructor builds the Searcher properly.
	 * </p>
	 */
	@Test
	public void testConstructor()
	{
		Searcher test = null;
		assertNull(test);
		test = initializeSearcher("AGCTAGCT", "AGCT");
		assertNotNull(test);
		assertNotNull(test.getText());
		assertNotNull(test.getSubstring());

		// System.out.println(test);
	}

	/**
	 * <p>
	 * This test verifies that my algorithm for making new test strings is
	 * making valid substrings.
	 * </p>
	 */
	@Test
	public void testIntToAlphabetString()
	{
		int runtimes = 100000;
		String testString = "";

		for (int i = 0; i < runtimes; i++)
		{
			testString = intToAlphabetString(randomInt());
			// System.out.println(testString);
			for (int j = 0; j < testString.length(); j++)
			{
				assertTrue(Alphabet.isValidLetter(testString.charAt(j)));
			}
		}
	}

	/**
	 * <p>
	 * This tests that the searcher properly searches for the substring in the
	 * source text, and that all matches are found.
	 * </p>
	 */
	@Test
	public void testSearch()
	{
		int runtimes = 100000;
		int digitizer = 3;
		int modulator = 0;

		Searcher test;
		String testText;
		String testSubstring;

		ArrayList<Integer> results1;
		ArrayList<Integer> results2;

		boolean atLeast1MatchFound = false;
		boolean atLeast1NoMatch = false;

		for (int i = 0; i < runtimes; i++)
		{
			testText = "";
			modulator = Alphabet.getSize();
			for (int j = 0; j < digitizer; j++)
			{
				testText += intToAlphabetString(randomInt())
						+ intToAlphabetString(randomInt());
				modulator *= Alphabet.getSize();
			}
			testSubstring = intToAlphabetString(randomInt() % modulator);
			test = initializeSearcher(testText, testSubstring);
			// System.out.println(test);
			results1 = test.search();
			results2 = ValidityCheck.staticSearch(testText, testSubstring);

			if (i % 1000 == 0)
			{
				System.out.println("Text: " + testText + "\nSubstring: "
						+ testSubstring + "\n" + test.getClass() + ": "
						+ results1 + "\nValidity: " + results2);
			}

			// Failed search cases get output.
			if (!results1.containsAll(results2)
					|| !results2.containsAll(results1))
			{
				System.out.println("Text: " + testText + "\nSubstring: "
						+ testSubstring + "\n" + test.getClass() + ": "
						+ results1 + "\nValidity: " + results2);
			}

			assertTrue(results1.containsAll(results2));
			assertTrue(results2.containsAll(results1));
			if (!atLeast1MatchFound && !results1.isEmpty())
			{
				atLeast1MatchFound = true;
			}
			if (!atLeast1NoMatch && results1.isEmpty())
			{
				atLeast1NoMatch = true;
			}
		}

		assertTrue(atLeast1MatchFound);
		assertTrue(atLeast1NoMatch);
	}

	/**
	 * <p>
	 * Random Test Cases that tripped one of the implementations up. Several
	 * were pulled out and kept separately to verify that these problem cases
	 * weren't issues anymore.
	 * </p>
	 */
	@Test
	public void testCase()
	{
		String testText = "GGCGTAAAGCGCCACTTACTCCATCCTGAGGAGGGAGTCTCCATGTTTGATTCCTCGTCCCGACCGATTTATTGCAATTGGAATCGCAGT";
		String testSubstring = "GGAG";
		Searcher test = initializeSearcher(testText, testSubstring);

		// System.out.println(test);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		System.out.println("Text: " + testText + "\nSubstring: " + testSubstring
				+ "\nBoyer Moore: " + results1 + "\nValidity: " + results2);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase1()
	{
		String testText = "CGCAGTTGGAAATGATCGCCTTGGTGTTGCCGCAGCGCTATAGAGCCTTAAACTCCAATGATACGAGCTGGAACTATGGCTGCGCGGGTTAGA";
		String testSubstring = "GGC";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase2()
	{
		String testText = "CGCAGTTAGGGTCCTACCCCTGTAGAAAAGCGCTCCGTTTTTAAGGATTTTCTCACGGGAAGTGCGTTGGATTGGTTTCTCTTGGTACTGACGA";
		String testSubstring = "CGC";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase3()
	{
		String testText = "GGAATACTATAACGTGAGATAAGCCTACAATTAAGTGAGTAATCCCCCCCCGAAGTTTCTGCGGGGTGGCCTATTATCCCCTTCAGATAAACT";
		String testSubstring = "A";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase4()
	{
		String testText = "CGCTGATCTTCGGGGCATATATTATAGGTCGAGACATATAGCGCCAGGCTTACATTTATCCTCAACATGAGAGGTATCCTTCTTGGGAAT";
		String testSubstring = "TCT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase5()
	{
		String testText = "CTGCGCCGGCCAGTTTACTGATATTGGACCCTAAGGAAAGGTGATCGAGTTGAAAAGTCACCAATTCGAATAATCATCAGGCCCGAAACTC";
		String testSubstring = "CGCC";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase6()
	{
		String testText = "CGAGATTTAAAGATTCCTCCATTTCTGAAAACTCAGCTTTCTGATGCCCTGACAGGGTGGCGCGCAGACAGATCCCTTAATGGGGACAAG";
		String testSubstring = "GAGA";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase7()
	{
		String testText = "CGCGATCCCCGGTCCTCGATAGTTTTCGGGGGCGCCCACTCCGAGCGTCACCTACGCATATTCCCCACTCGTTTAATGGACAGGGATTCCC";
		String testSubstring = "TTCC";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase8()
	{
		String testText = "ACGT";
		String testSubstring = "ACGT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase9()
	{
		String testText = "AAAACCCGGT";
		String testSubstring = "AAAACCCGGT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase10()
	{
		String testText = "AAAAACCCCGGGTT";
		String testSubstring = "AAAAACCCCGGGTT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase11()
	{
		String testText = "AAATACCCCGGGTA";
		String testSubstring = "AAATACCCCGGGTA";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase12()
	{
		String testText = "ACGTACGT";
		String testSubstring = "ACGT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase13()
	{
		String testText = "ACGTACGTACGT";
		String testSubstring = "ACGT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase14()
	{
		String testText = "AAAAACCCCGGGTTAAAAACCCCGGGTT";
		String testSubstring = "AAAAACCCCGGGTT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase15()
	{
		String testText = "AAATACCCCGGGTAAAATACCCCGGGTAAAATACCCCGGGTA";
		String testSubstring = "AAATACCCCGGGTA";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase16()
	{
		String testText = "CCACGTGTTGCATCAGCGCCCGCGGCGTGTCGGGTCAAACCTTTCCGCGTGGCGTCCCCGAAACTCCAGAATCTACAAACCTGCTAAATCGCCAT";
		String testSubstring = "CCAC";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase17()
	{
		String testText = "CCACGTGTTGCATCAGCGCCCGCGGCGTGTCGGGTCAAACCTTTCCGCGTGGCGTCCCCGAAACTCCAGAATCTACAAACCTGCTAAATCGCCAT";
		String testSubstring = "TCCG";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase18()
	{
		String testText = "CCACGTGTTGCATCAGCGCCCGCGGCGTGTCGGGTCAAACCTTTCCGCGTGGCGTCCCCGAAACTCCAGAATCTACAAACCTGCTAAATCGCCAT";
		String testSubstring = "GACT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}

	@Test
	public void testCase19()
	{
		String testText = "TGAAAAGGTATCTTTTGACACATAATCTGCCTCGATCCTAATGCGTAATTGCATCCGGAACTACGGCTAGCTGGTCGTCAACTCCC";
		String testSubstring = "CCCT";
		Searcher test = initializeSearcher(testText, testSubstring);
		ArrayList<Integer> results1 = test.search();
		ArrayList<Integer> results2 = ValidityCheck.staticSearch(testText,
				testSubstring);

		// System.out.println("Text: " + testText + "\nSubstring: " +
		// testSubstring
		// + "\n" + test.getClass() + ": " + results1 + "\nValidity: "
		// + results2 + "\nText Length: " + testText.length());

		assertTrue(results1.containsAll(results2));
		assertTrue(results2.containsAll(results1));
	}
}
