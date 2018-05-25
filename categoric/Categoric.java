package categoric;

import java.util.ArrayList;
import searcher.Searcher;
import shiftlist.LetterList;
import shiftlist.RichLetterListIterator;
import shiftlist.ShiftList;
import traditional.Traditional1;

public class Categoric extends Traditional1
{
	/*
	 * Shift List generated for the substring. This is used to compare to find
	 * matches. Direct character comparisons between the substring and text
	 * never occur.
	 */
	private ShiftList substringShift;

	public Categoric(String text, String substring)
	{
		this(new ShiftList(text), substring);
		substringShift = new ShiftList(substring);
	}

	public Categoric(ShiftList shiftList, String substring)
	{
		super(shiftList, substring);
		substringShift = new ShiftList(substring);
	}

	public ArrayList<Integer> search()
	{
		// initialize comparisons count
		comparisons = 0;
		/*
		 * Create an iterator for iterating through letters by order of least
		 * occuring to most occurring, with the added stipulation that the first
		 * letter chosen is contained within the substring.
		 */
		CategoricIterator listIterator = new CategoricIterator(shiftList,
				substring);

		/*
		 * Build the ArrayList full of potential matches by doing a positive
		 * search on the first letter (the iterator ensures that the first
		 * letter to check is contained in the substring).
		 */
		ArrayList<Integer> results = firstLetterCheck(listIterator.next());
		RichLetterListIterator iterator;

		/*
		 * Starting from the second letter in the iterator list and checking all
		 * letters except the last, this does a positive check if the next least
		 * occurring character occurs in the substring, and an absence check if
		 * the letter does not. The most occurring character in the text (or the
		 * most occurring positive check letter, if applicable) is last, and is
		 * skipped.
		 */
		for (int i = 1; i < listIterator.getSize() - 1; i++)
		{
			iterator = listIterator.next();

			if (iterator.isContainedInSubstring())
			{
				/*
				 * This checks by doing a positive check (checking that
				 * characters are where we expect them to be). Indices in the
				 * potential matches ArrayList are removed if they fail this
				 * check.
				 */
				results = positiveCheck(iterator, results);
			} else
			{
				/*
				 * This checks by doing an absence check (checking that no
				 * characters of this letter appear in the expected range).
				 * Indices in the potential matches ArrayList are removed if
				 * they fail this check.
				 */
				results = absenceCheck(iterator, results);
			}
		}

		return results;
	}

	/**
	 * <p>
	 * This builds the list of potential matches based off the least occurring
	 * positive check (that is, the letter that occurs the least in the source
	 * text and is also contained in the substring).
	 * </p>
	 * 
	 * @param iterator
	 * @return
	 */
	private ArrayList<Integer> firstLetterCheck(RichLetterListIterator iterator)
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		LetterList substringList = substringShift
				.getLetterList(iterator.getLetter());
		int left = iterator.viewPosition(substringList.get(0));

		for (index = left; index < iterator.size() - substringList.size() + 1
				&& iterator.nextByPosition(index + substringList.size() - 1)
						+ iterator.getOffset() < text.length(); index++)
		{
			verifyPositive(iterator, results);
		}
		return results;
	}

	/**
	 * <p>
	 * This is analogous to the verifyMatch method used by all the other
	 * Searcher classes. For positive check potential matches, this goes
	 * character by character for characters of a specific letter and verifies
	 * that they appear where they are expected to be.
	 * </p>
	 * 
	 * <p>
	 * Because of how this algorithm works, in order to verify matches by
	 * alphabet letter, more rigorous checks are needed. Specifically, this
	 * method also checks that the letter being verified here does not appear in
	 * the potential match's range (before or after the character matches, but
	 * still within the bounds of the substring within the potential match
	 * range).
	 * </p>
	 * 
	 * @param iterator
	 * @param results
	 */
	private void verifyPositive(RichLetterListIterator iterator,
			ArrayList<Integer> results)
	{
		LetterList substringList = substringShift
				.getLetterList(iterator.getLetter());
		int shift = substringList.get(0);
		int iteratorStart = iterator.nextByPosition(index);
		int offset = 1;

		// Absence Before Check
		if (index == 0
				|| iterator.nextByPosition(index - 1) < iteratorStart - shift)
		{

			// Verify character by character
			while (offset < substringList.size()
					&& index + offset < iterator.size()
					&& iterator.nextByPosition(index + offset)
							- iteratorStart == substringList.get(offset)
									- shift)
			{
				comparisons++;
				offset++;
			}

			// Absence After Check
			if (offset == substringList.size()
					&& (index + offset == iterator.size() || iterator
							.nextByPosition(index + offset) > iterator
									.nextByPosition(index + offset - 1)
									+ iterator.getOffset()))
			{
				results.add(iterator.nextByPosition(index) - shift);
			}
		}
	}

	/**
	 * <p>
	 * This checks by doing a positive check (checking that characters are where
	 * we expect them to be). Indices in the potential matches ArrayList are
	 * removed if they fail this check. This is for letters that are contained
	 * in the substring.
	 * </p>
	 * 
	 * @param iterator
	 * @param potentialResults
	 * @return
	 */
	private ArrayList<Integer> positiveCheck(RichLetterListIterator iterator,
			ArrayList<Integer> potentialResults)
	{
		ArrayList<Integer> results = new ArrayList<Integer>(
				potentialResults.size());
		int matchCheck = -1;
		LetterList substringList = substringShift
				.getLetterList(iterator.getLetter());
		int shift = substringList.get(0);

		for (Integer potentialMatch : potentialResults)
		{
			matchCheck = iterator.viewPosition(potentialMatch + shift);

			if (iterator.nextByPosition(matchCheck) == potentialMatch + shift)
			{
				index = matchCheck;
				verifyPositive(iterator, results);

				// int offset = 1;
				// int iteratorStart = iterator.nextByPosition(matchCheck);
				// int substringStart = substringList.get(0);
				// while (offset < substringList.size()
				// && matchCheck + offset < iterator.size()
				// && iterator.nextByPosition(matchCheck + offset)
				// - iteratorStart == substringList.get(offset)
				// - substringStart)
				// {
				// offset++;
				// }
				//
				// if (offset == substringList.size())
				// {
				// results.add(potentialMatch);
				// }
			}

		}

		return results;
	}

	/**
	 * <p>
	 * This checks by doing an absence check (checking that no characters of
	 * this letter appear in the expected range). Indices in the potential
	 * matches ArrayList are removed if they fail this check. This is for
	 * letters that are not contained in the substring.
	 * </p>
	 * 
	 * @param iterator
	 * @param potentialResults
	 * @return
	 */
	private ArrayList<Integer> absenceCheck(RichLetterListIterator iterator,
			ArrayList<Integer> potentialResults)
	{
		ArrayList<Integer> results = new ArrayList<Integer>(
				potentialResults.size());
		int matchCheck = -1;
		int matchCheckLeft = -3;
		int matchCheckRight = -4;
		int substringEnd = -2;

		/*
		 * For every potential result in the list, we check that no instances of
		 * this letter (which is not contained in our substring) occur where the
		 * substring should be. If the potential result meets our expectations,
		 * it's added to the new results list.
		 */
		for (Integer potentialMatch : potentialResults)
		{
			comparisons++;

			/*
			 * Right-most boundary that the character cannot appear in for this
			 * to be a valid match.
			 */
			substringEnd = potentialMatch + substring.length();
			/*
			 * Use binary search to find the index of the left-most position
			 * relative to our potential match to start searching for.
			 */
			matchCheck = iterator.viewPosition(potentialMatch);

			/*
			 * Check that this position does not occur within the
			 * potentialMatch's area.
			 */
			if (iterator.nextByPosition(matchCheck) < potentialMatch
					|| iterator.nextByPosition(matchCheck) >= substringEnd)
			{
				/*
				 * Check the next instance of this letter to the left in the
				 * source text of the potentialMatch. Verify that this does not
				 * occur within the potentialMatch's area. All other instances
				 * left will be further away from our potentialMatch, and
				 * therefore do not need to be checked.
				 */
				matchCheckLeft = matchCheck - 1;
				comparisons++;
				if (matchCheckLeft < 0 || iterator
						.nextByPosition(matchCheckLeft) < potentialMatch)
				{
					/*
					 * Check the next instance of this letter to the right in
					 * the source text of the potentialMatch. Verify that this
					 * does not occur within the potentialMatch's area. All
					 * other instances right will be further away from our
					 * potentialMatch, so don't need to be checked.
					 */
					matchCheckRight = matchCheck + 1;
					comparisons++;
					if (matchCheckRight >= iterator.size() || iterator
							.nextByPosition(matchCheckRight) >= substringEnd)
					{
						results.add(potentialMatch);
					}
				}
			}
		}

		return results;
	}

	public Searcher createNew(String text, String substring)
	{
		return new Categoric(text, substring);
	}

	public Searcher createNew(ShiftList list, String substring)
	{
		return new Categoric(list, substring);
	}
}
