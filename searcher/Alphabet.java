package searcher;

/**
 * <p>
 * This enum stores the alphabet of DNA proteins that this project will work
 * with.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public enum Alphabet
{
	A(
			'A', "adenine"
	), C(
			'C', "cytosine"
	), G(
			'G', "guanine"
	), T(
			'T', "thymine"
	);

	/**
	 * <p>
	 * This is the letter that will be parsed from the texts as a letter in the
	 * alphabet. This corresponds to one of the 4 proteins in DNA.
	 * </p>
	 * 
	 */
	private char letter;
	/**
	 * <p>
	 * This is the name of the protein written out.
	 * </p>
	 */
	private String protein;
	/**
	 * <p>
	 * This is the cached position of the letter in the alphabet, creating a
	 * total ordering for letters in the alphabet. This is set the first time
	 * the position is queried by the getter method for positions, and can be
	 * manually set using the static setPositions() method.
	 * </p>
	 */
	private int position;

	/**
	 * <p>
	 * This caches the position of the letter in the array of alphabet letters
	 * so that it can be summoned without iterating over the array each time.
	 * </p>
	 */
	public static void setPositions()
	{
		Alphabet[] alphabet = Alphabet.values();
		for (int i = 0; i < alphabet.length; i++)
		{
			alphabet[i].position = i;
		}
	}

	/**
	 * <p>
	 * Getter Method for the number of letters in the alphabet.
	 * </p>
	 * 
	 * @return Returns the total number of letters in the alphabet.
	 */
	public static int getSize()
	{
		return Alphabet.values().length;
	}

	public static boolean contains(char letter)
	{
		Alphabet[] alphabet = Alphabet.values();
		for (Alphabet a : alphabet)
		{
			if (a.getLetter() == letter)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Given an index position number that is within the bounds of our
	 * alphabet's size (that is, a number greater than or equal to zero, and
	 * less than the number returned by the method getSize(), this returns the
	 * letter value associated with that position in the ordering of our
	 * alphabet.
	 * </p>
	 * 
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public static Alphabet getLetter(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index > getSize())
		{
			throw new IndexOutOfBoundsException(
					"Attempted to query an invalid index position.  Position values passed into this method should be greater than or equal to 0 and less than "
							+ getSize());
		}

		return Alphabet.values()[index];
	}

	/**
	 * <p>
	 * This method is mostly used for junit testing.
	 * </p>
	 * <p>
	 * This method checks if the letter passed in exists in the alphabet.
	 * </p>
	 * 
	 * @param letter
	 *            - Letter we are checking to see if it's a valid letter in the
	 *            alphabet.
	 * @return Returns <b>true</b> if the letter is in the alphabet. Returns
	 *         <b>false</b> if not.
	 */
	public static boolean isValidLetter(char letter)
	{
		Alphabet[] alphabet = Alphabet.values();
		for (int i = 0; i < alphabet.length; i++)
		{
			if (letter == alphabet[i].getLetter())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * This constructor sets the name of the protein corresponding to each
	 * letter in the alphabet, and stores the char character for each letter
	 * that can be parsed from texts and substrings this will be working with.
	 * <p>
	 * </p>
	 * Position is set with an uninitialized value of -1 to start, which will
	 * subsequently be set through the static method setPositions() to it's
	 * actual value, corresponding to it's position in the values() array for
	 * this enum.
	 * </p>
	 * 
	 * @param letter
	 *            - Letter in the alphabet being represented. This letter will
	 *            be parsed from the texts and substrings this program will be
	 *            working with.
	 * @param protein
	 *            - This is a string representation of the name of the protein
	 *            this alphabet letter corresponds to.
	 */
	private Alphabet(char letter, String protein)
	{
		this.letter = letter;
		this.protein = protein;
		position = -1;
	}

	/**
	 * <p>
	 * Getter method for the letter being represented.
	 * </p>
	 * 
	 * @return Returns a parseable character for this letter in the alphabet.
	 */
	public char getLetter()
	{
		return letter;
	}

	/**
	 * <p>
	 * Getter method for the string representation of the protein this letter
	 * corresponds to.
	 * </p>
	 * 
	 * @return Returns the string representation of the protein that corresponds
	 *         to this letter.
	 */
	public String getProtein()
	{
		return protein;
	}

	/**
	 * <p>
	 * Getter method for the cached position of this letter in the enum values()
	 * array. This represents the total ordering of letters in the alphabet.
	 * </p>
	 * <p>
	 * This checks to make sure that positions are initialied before outputting,
	 * guaranteeing that the uninitialied value of -1 is never output. If the
	 * position value is uninitialized, this method calls the static
	 * setPositions() method to ensure that the position value is set to its
	 * proper value.
	 * </p>
	 * 
	 * @return Returns the position of the letter in the enum values() array,
	 *         which is this letter's placement in the total ordering of letters
	 *         in the Alphabet.
	 */
	public int getPosition()
	{
		if (position == -1)
		{
			Alphabet.setPositions();
		}

		return position;
	}

	@Override
	public String toString()
	{
		return "" + letter;
	}

}
