package traditional;

import java.util.ArrayList;
import searcher.Searcher;
import shiftlist.ShiftList;

public abstract class Traditional extends Searcher
{
	protected ShiftList shiftList;

	/**
	 * <p>
	 * In order to speed up testing, this constructor allows you to pass in a
	 * pre-built ShiftList rather than building it all over again.
	 * </p>
	 * 
	 * @param shiftList
	 * @param substring
	 */
	public Traditional(ShiftList shiftList, String substring)
	{
		super(shiftList.getText(), substring);
		this.shiftList = shiftList;
	}

	public Traditional(String text, String substring)
	{
		this(new ShiftList(text), substring);
	}

	protected abstract void setPositionIterator();

	public ShiftList getShiftList()
	{
		return shiftList;
	}

	@Override
	public ArrayList<Integer> search()
	{
		setPositionIterator();
		return super.search();
	}
}
