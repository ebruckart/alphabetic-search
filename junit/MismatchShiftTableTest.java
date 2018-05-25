package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import horspools.MismatchShiftTable;
import searcher.Alphabet;
import searcher.ShiftTable;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class MismatchShiftTableTest
{
	@Test
	public void testConstructor()
	{
		String substring = "test";
		ShiftTable test = null;

		assertNull(test);
		test = initializeShiftTable(substring);

		assertNotNull(test);
		assertEquals(substring, test.getSubstring());
	}

	public ShiftTable initializeShiftTable(String substring)
	{
		return new MismatchShiftTable(substring);
	}

	@Test
	public void testShiftSubstringLength1()
	{
		String substring = "";
		ShiftTable test = null;

		// This iterates through all possible values of substrings of length 1
		for (int i = 0; i < Alphabet.getSize(); i++)
		{
			substring = "" + Alphabet.getLetter(i).getLetter();
			test = initializeShiftTable(substring);
			for (int j = 0; j < Alphabet.getSize(); j++)
			{
				/*
				 * All instances of shift tables created from substrings of
				 * length 1 should return a shift value of 1, which is also the
				 * length of the substring lol.
				 */
				assertEquals(substring.length(), test.getShift(j));
				assertEquals(1, test.getShift(j));
			}
		}
	}

	@Test
	public void testShiftSubstringLength2()
	{
		String substring = "";
		ShiftTable test = null;

		/*
		 * This iterates through many values of substrings of length 2
		 */
		for (int i = Alphabet.getSize(); i < (Alphabet.getSize()
				* Alphabet.getSize()); i++)
		{
			substring = SearcherTest.intToAlphabetString(new Integer(i));
			test = initializeShiftTable(substring);

			for (int j = 0; j < Alphabet.getSize(); j++)
			{
				if (substring.charAt(0) == Alphabet.getLetter(j).getLetter())
				{
					assertEquals(1, test.getShift(j));
				} else if (substring.charAt(1) == Alphabet.getLetter(j)
						.getLetter())
				{
					assertEquals(2, test.getShift(j));
				} else
				{
					assertEquals(2, test.getShift(j));
				}
			}
		}
	}

	@Test
	public void testShiftSubstringLength3()
	{
		String substring = "";
		ShiftTable test = null;

		/*
		 * This iterates through many values of substrings of length 3
		 */
		for (int i = (Alphabet.getSize() * Alphabet.getSize()); i < (Alphabet
				.getSize() * Alphabet.getSize() * Alphabet.getSize()); i++)
		{
			substring = SearcherTest.intToAlphabetString(new Integer(i));
			test = initializeShiftTable(substring);

			for (int j = 0; j < Alphabet.getSize(); j++)
			{
				if (substring.charAt(1) == Alphabet.getLetter(j).getLetter())
				{
					assertEquals(1, test.getShift(j));
				} else if (substring.charAt(0) == Alphabet.getLetter(j)
						.getLetter())
				{
					assertEquals(2, test.getShift(j));
				} else
				{
					assertEquals(3, test.getShift(j));
				}
			}
		}
	}

	@Test
	public void testShift()
	{
		String substring = "";
		ShiftTable test = null;
		int runtimes = 100;

		for (int i = 0; i < runtimes; i++)
		{
			substring = SearcherTest
					.intToAlphabetString(SearcherTest.randomInt());
			test = initializeShiftTable(substring);

			/*
			 * Not going to worry about random substrings of length 1, they've
			 * been checked by a stand-alone method, and they make the logic
			 * here hairier.
			 */
			if (substring.length() > 1)
			{
				for (int j = 0; j < Alphabet.getSize(); j++)
				{
					/*
					 * If the substring (minus the last character) contains the
					 * letter in question, then our get shift derivation should
					 * fetch that letter for us from the substring.
					 */
					if (substring.substring(0, substring.length() - 1)
							.contains("" + Alphabet.getLetter(j).getLetter()))
					{
						assertEquals(
								substring.charAt(substring.length() - 1
										- test.getShift(j)),
								Alphabet.getLetter(j).getLetter());
					} else
					{
						/*
						 * If the substring minus the last character does not
						 * contain the letter in question, then the shift should
						 * be the size of the entire substring length.
						 */
						assertEquals(substring.length(), test.getShift(j));
					}
				}
			}
		}
	}

	@Test
	public void testShiftExceptions()
	{
		String substring = "A";
		ShiftTable test = initializeShiftTable(substring);

		// Testing for index below expected (-1).
		try
		{
			test.getShift(-1);
			fail("Expected an exception to be thrown but was not thrown.");
		} catch (IndexOutOfBoundsException e)
		{
			System.out.println("Expected Exception caught for shift of -1.");
		}

		// Testing for index above expected (alphabet's size).
		try
		{
			test.getShift(Alphabet.getSize());
			fail("Expected an exception to be thrown but was not thrown.");
		} catch (IndexOutOfBoundsException e)
		{
			System.out
					.println("Expected Exception caught for shift on position "
							+ Alphabet.getSize());
		}
	}

}
