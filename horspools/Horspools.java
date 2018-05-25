package horspools;

import searcher.Alphabet;
import searcher.Searcher;

/**
 * <p>
 * This is an implementation of the Horspools algorithm. This is specialized to
 * work on DNA sequences with 4 characters, as defined in the Alphabet enum.
 * Operation is undefined if the alphabet contains characters not in the
 * Alphabet enum.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Horspools extends Searcher
{
	protected MismatchShiftTable shiftTable;

	public Horspools(String text, String substring)
			throws NullPointerException, IndexOutOfBoundsException
	{
		super(text, substring);
		shiftTable = new MismatchShiftTable(substring);
	}

	@Override
	protected void generateSkip(Alphabet badCharacter, int offset)
	{
		index += shiftTable.getShift(badCharacter);
	}

	@Override
	public Searcher createNew(String text, String substring)
	{
		return new Horspools(text, substring);
	}
}