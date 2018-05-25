package experiment;

import searcher.Searcher;
import shiftlist.ShiftList;
import traditional.Traditional1;
import traditional.Traditional2;
import horspools.Horspools;
import categoric.Categoric;
import boyerMoore.BoyerMoore;

/**
 * <p>
 * This enum is used by the checker experimental class to determine which type
 * of searcher to run the experiment with.
 * </p>
 * 
 * TODO BoyerMoore not BMAlt
 * 
 * @author Emily Bruckart
 *
 */
public enum SearcherType
{
	horspools(
			"horspools", false,
			new Horspools(Searcher.dummyText, Searcher.dummySubstring)
	), boyermoore(
			"boyermoore", false,
			new BoyerMoore(Searcher.dummyText, Searcher.dummySubstring)
	), traditional1(
			"traditional1", true,
			new Traditional1(Searcher.dummyText, Searcher.dummySubstring)
	), traditional2(
			"traditional2", true,
			new Traditional2(Searcher.dummyText, Searcher.dummySubstring)
	), categorical(
			"categorical", true,
			new Categoric(Searcher.dummyText, Searcher.dummySubstring)
	), validity(
			"validity", false,
			new Searcher(Searcher.dummyText, Searcher.dummySubstring)
	);

	public static final SearcherType defaultType = SearcherType.horspools;
	private Searcher searcherType;
	private String type;
	private boolean usesShiftList;

	private SearcherType(String type, boolean usesShiftList,
			Searcher searcherType)
	{
		this.type = type;
		this.searcherType = searcherType;
		this.usesShiftList = usesShiftList;
	}

	public Searcher createSearcher(String text, String substring)
	{
		return searcherType.createNew(text, substring);
	}

	public Searcher createSearcher(ShiftList list, String substring)
	{
		return searcherType.createNew(list, substring);
	}

	public String toString()
	{
		return type;
	}

	public boolean usesShiftList()
	{
		return usesShiftList;
	}
}
