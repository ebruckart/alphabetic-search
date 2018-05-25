package junit;

import searcher.Searcher;
import traditional.Traditional1;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Traditional1Test extends HorspoolsTest
{

	@Override
	public Searcher initializeSearcher(String text, String substring)
	{
		return new Traditional1(text, substring);
	}
}
