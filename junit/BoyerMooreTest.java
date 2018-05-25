package junit;

import boyerMoore.BoyerMoore;
import searcher.Searcher;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class BoyerMooreTest extends SearcherTest
{

	/**
	 * <p>
	 * This is the only method of the parent class SearcherTest that needed to
	 * be overwritten to execute all the same tests as were conducted on the
	 * default Searcher for the Boyer Moore Algorithm.
	 * </p>
	 * 
	 * <p>
	 * As such, most of the tests run by this class are inherited from the
	 * parent SearcherTest, just using Boyer Moore imeplementation instead of
	 * Searcher implementation.
	 * </p>
	 */
	@Override
	public Searcher initializeSearcher(String text, String substring)
	{
		return new BoyerMoore(text, substring);
	}

}
