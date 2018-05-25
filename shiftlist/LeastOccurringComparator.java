package shiftlist;

import java.util.Comparator;
import searcher.Alphabet;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class LeastOccurringComparator implements Comparator<Alphabet>
{
	private ShiftList list;

	public LeastOccurringComparator(ShiftList list)
	{
		this.list = list;
	}

	public int compare(Alphabet arg0, Alphabet arg1)
	{
		return list.getLetterList(arg0).size()
				- list.getLetterList(arg1).size();
	}

}
