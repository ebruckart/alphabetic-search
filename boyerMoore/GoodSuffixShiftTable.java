package boyerMoore;

import searcher.ShiftTable;

/**
 * <p>
 * This is the good partial match shift table used in the Boyer Moore algorithm.
 * It is one of the two tables used by the Boyer Moore algorithm to determine
 * how far to the right the algorithm can shift before checking for matches of
 * the substring in the source text.
 * </p>
 * 
 * <p>
 * References: <a
 * href=https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
 * heuristic/>https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
 * heuristic/</a>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class GoodSuffixShiftTable extends ShiftTable
{

	/**
	 * <p>
	 * This constructor saves the substring that the table was built with and
	 * builds the good partial match shift table.
	 * </p>
	 * 
	 * @param substring
	 *            - This is the substring the table will be built using.
	 */
	public GoodSuffixShiftTable(String substring)
	{
		super(substring);
	}

	/**
	 * <p>
	 * Reference: <a
	 * href=https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
	 * heuristic/>https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
	 * heuristic/</a>
	 * </p>
	 * 
	 * <p>
	 * 3 Cases, given a subset String t of the sought substring T, searching in
	 * the source text P:
	 * </p>
	 * <ol>
	 * <li>Another occurrence of t in P matched with t in T (strong suffix
	 * rule)</li>
	 * <li>A prefix of P is found that matches with suffix of t in T</li>
	 * <li>P moves past t</li>
	 * </ul>
	 */
	protected void buildTable()
	{
		shiftTable = new int[substring.length()];
		int[] borderPosition = new int[substring.length()];

		case2(borderPosition);
		case1(borderPosition);
	}

	/**
	 * <p>
	 * Preprocessing for case 1, the strong good suffix rule
	 * </p>
	 * 
	 * <p>
	 * Reference: <a
	 * href=https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
	 * heuristic/>https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
	 * heuristic/</a>
	 * </p>
	 */
	private void case1(int[] borderPosition)
	{
		int left = substring.length() - 1;
		int right = substring.length();
		borderPosition[left] = right;

		while (left > 0)
		{
			while (right < substring.length() && substring
					.charAt(left - 1) != substring.charAt(right - 1))
			{
				if (shiftTable[right] == 0)
				{
					shiftTable[right] = right - left;
				}
				right = borderPosition[right];
			}
			left--;
			right--;
			borderPosition[left] = right;
		}
	}

	/**
	 * <p>
	 * Preprocessing for case 2
	 * </p>
	 * 
	 * <p>
	 * Reference: <a
	 * href=https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
	 * heuristic/>https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-
	 * heuristic/</a>
	 * </p>
	 */
	private void case2(int[] borderPosition)
	{
		int left;
		int right;
		right = borderPosition[0];
		for (left = 0; left < substring.length(); left++)
		{
			if (shiftTable[left] == 0)
				shiftTable[left] = right;

			if (left == right)
			{
				right = borderPosition[right];
			}
		}
	}

}
