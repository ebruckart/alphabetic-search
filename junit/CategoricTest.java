package junit;

import categoric.Categoric;
import searcher.Searcher;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class CategoricTest extends SearcherTest
{

	// @Override
	public Searcher initializeSearcher(String text, String substring)
	{
		return new Categoric(text, substring);
	}
}
