package searcher;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public abstract class ShiftTable
{
	/**
	 * <p>
	 * This is the substring the shift table was built using.
	 * </p>
	 */
	protected String substring;

	/**
	 * <p>
	 * This is the shift table that determines how far over we can shift in the
	 * method getShift. Specifics of how this works depends on the
	 * implementation.
	 * </p>
	 */
	protected int[] shiftTable;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param substring
	 */
	public ShiftTable(String substring)
	{
		this.substring = substring;
		buildTable();
	}

	protected abstract void buildTable();

	public int getShift(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= shiftTable.length)
		{
			throw new IndexOutOfBoundsException(this.getClass().getName()
					+ " :: getShift error: passed in an index value of " + index
					+ ", which is not a valid index position."
					+ "  Index value should have been between 0 and "
					+ (shiftTable.length - 1));
		}

		return shiftTable[index];
	}

	/**
	 * <p>
	 * Getter method for the substring used to create the shift table. Used
	 * mainly in testing to verify that the table was built correctly.
	 * </p>
	 * 
	 * @return Returns the substring that was used to build the bad character
	 *         mismatch shift table.
	 */
	public String getSubstring()
	{
		return substring;
	}

}
