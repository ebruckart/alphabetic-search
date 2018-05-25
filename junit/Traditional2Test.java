package junit;

import searcher.Searcher;
import traditional.Traditional2;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Traditional2Test extends SearcherTest
{

	@Override
	public Searcher initializeSearcher(String text, String substring)
	{
		return new Traditional2(text, substring);
	}
}
