package boyerMoore;

import horspools.Horspools;
import searcher.Alphabet;
import searcher.Searcher;

/**
 * <p>
 * This is an implementation of the Boyer Moore Algorithm. This is specialized
 * to work on DNA sequences, whose alphabet is set in the Alphabet enum.
 * Execution is undefined when Strings containing characters not defined in the
 * Alphabet are used.
 * </p>
 * <p>
 * It extends the Horspools implementation class because it uses the bad
 * character mismatch shift table used in the Horspools algorithm, which it
 * inherits from that parent class. This uses both the bad character mismatch
 * shift table and the good partial match shift table to determine how many
 * characters can be skipped when checking for matches of the substring in the
 * source text.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class BoyerMoore extends Horspools
{
	private GoodSuffixShiftTable partialMatch;

	public BoyerMoore(String text, String substring)
			throws NullPointerException, IndexOutOfBoundsException
	{
		super(text, substring);
		partialMatch = new GoodSuffixShiftTable(substring);
	}

	/**
	 * increment index to check for the next possible match of the substring in
	 * the text. This is an implementation of the Boyer Moore algorithm, so it
	 * uses both the mismatch shift table and the good partial match table to
	 * determine how many characters can be skipped.
	 */
	@Override
	protected void generateSkip(Alphabet badCharacter, int offset)
	{
		index += Math.max(shiftTable.getShift(badCharacter),
				partialMatch.getShift(offset));
	}

	public Searcher createNew(String text, String substring)
	{
		return new BoyerMoore(text, substring);
	}
}
