package junit;

import horspools.Horspools;
import searcher.*;

/**
 * <p>
 * This extends the SearcherTest class to do the searcher tests using a
 * Horspools optimized searcher.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class HorspoolsTest extends SearcherTest
{

	/**
	 * <p>
	 * This is the only method of the parent class SearcherTest that needed to
	 * be overwritten to execute all the same tests as were conducted on the
	 * default Searcher for the Horspools Algorithm.
	 * </p>
	 * 
	 * <p>
	 * As such, most of the tests run by this class are inherited from the
	 * parent SearcherTest, just using Horspools imeplementation instead of
	 * Searcher implementation.
	 * </p>
	 */
	@Override
	public Searcher initializeSearcher(String text, String substring)
	{
		return new Horspools(text, substring);
	}

	public Searcher initializeAlternate(String text, String substring)
	{
		return new Searcher(text, substring);
	}
}
