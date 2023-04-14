import javax.xml.crypto.Data;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main201522204 {

	public static ArrayList<String> ReadData(String pathname) {
		ArrayList<String> strlist = new ArrayList<String>();
		try {

			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			int j = 0;
			String line = "";
			while ((line = br.readLine()) != null) {
				strlist.add(line);
			}
			return strlist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strlist;
	}

	public static ArrayList<ArrayList<Integer>> DataWash(ArrayList<String> Datalist) {
		ArrayList<ArrayList<Integer>> AIS = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> IS = new ArrayList<Integer>();
		for (int i = 0; i < Datalist.size(); i++) {
			String Tstr = Datalist.get(i);
			if (Tstr.equals("A") == false) {
				IS.add(Integer.parseInt(Tstr));
			}
			if (Tstr.equals("A")) {
				ArrayList<Integer> elemAIS = new ArrayList<Integer>(IS);
				AIS.add(elemAIS);
				IS.clear();
			}
		}
		return AIS;
	}

//%%%%%%%%%%%%%%%%%%%%%%% Procedure LongestComb() that will contain your code:

	public static int LongestComb(int[] A, int n) {
		/*
		 * Here should be the description of the main ideas of your solution:
		 * 
		 * To find the longest anchored comb, find the combs whose first and last tooth
		 * have the same first elements. Then calculate the length of each comb, and
		 * find the maximum value among them.
		 * 
		 * In general, the problem has been divided into two parts: find same elements
		 * and calculate length of comb with no need to consider anchored. If we
		 * calculate the length of comb while finding same elements, the complexity will
		 * be the multiplication of these two complexity, so I find same elements first,
		 * then calculate the length, the complexity will be the addition of these two
		 * complexity.
		 * 
		 * Create an an ArrayList to store the information of non-duplicated same first
		 * elements. The same elements may appear more than two times, but we only
		 * require the farthest one. Use two for-loop to iterate through the input
		 * array. Set i=0 and increase it. Last element of sequence cannot be the first
		 * element of tooth, so set j=n-2 and decrease it. We always find the left most
		 * i and right most j that satisfy the condition. If A[i] = A[j], check A[i] <
		 * A[i+1] and A[j] < A[j+1], if so, both of them are tooth. Then check whether
		 * this value has already existed in the ArryaList, if not, store the same value
		 * A[i] and indexes i and j into the ArrayList.
		 * 
		 * Iterate through the ArrayList, get two indexes, copy a sub-array starts and
		 * ends at two indexes. The first and last elements of sub-array will be the
		 * first elements of the first and last tooth. For each sub-array, calculate the
		 * length of longest comb.
		 * 
		 * Define n be the length of sub-array. Define L(i) be the length of longest
		 * comb that ends at position i, i = 0,1,2,...,n-2. If we are able to compute
		 * these L(i) values, then the solution to our problem is equal to L = 1 +
		 * max_{L[i]: A[i] > A[n-1]}. Plus 1 because we add the last tooth that be
		 * anchored with first tooth. So A[n-1] should be the first element of last
		 * tooth, and the second element of the previous tooth needs to be bigger than
		 * the first element of last tooth.
		 * 
		 * Set L[0] = 0 and L[1] = 1, because the first two elements is a tooth. If we
		 * have computed L[0],L[1],...L[i-1] for some i, to find L[i], we compute L[i] =
		 * 1 + max_{L[j]: A[j] > A[i-1] and j < i-1} if A[i-1] < A[i], else keep L[i] =
		 * 0.
		 * 
		 * In other words, check A[i-1] < A[i], so it is a tooth. Then check all A[j], j
		 * < i-1, find those that are larger than A[i-1]. We do this because the second
		 * element of the former tooth needs to be bigger than the first element of the
		 * latter tooth. If A[j] > A[i-1] < A[i], we can increase the longest comb that
		 * ends at j by one more value. Else, A[i-1] >= A[i], it is not a tooth, then
		 * define L[i] = 0.
		 * 
		 * Calculate the length of longest anchored comb for each sub-array, return the
		 * maximum value. If there is no same element in the sequence, we check if there
		 * is a comb with one tooth, if it exits, return 1; or else, it is a
		 * non-increasing sequence, then return 0.
		 */

		/*
		 * Here should be the statement and description of the running time analysis of
		 * your implementation:
		 *
		 * Finding all pairs of teeth with the same first elements requires us to check
		 * each pair of value, i.e. O(n-2 + n-3 + ... + 1) = O(n^2) time. And for each
		 * value we need to check whether it has already existed in ArrayList. There are
		 * at most n/2 values need to be checked in the case that each value appears two
		 * times, i.e. O(n/2) time. So, in total, find all pairs of farthest teeth with
		 * the same first elements takes O(n^2 * n/2) = O(n^3) time.
		 * 
		 * Each pair of farthest teeth can be an anchored comb. For each anchored comb,
		 * computing the length of longest anchored comb. Computing L[i] requires us to
		 * check all of the values L[0],...,L[i-2], i.e. O(i) times. computing all of
		 * the L[i] values takes O(1+2+3+...+n) = O(n^2) time. There are at most n/2
		 * pairs of teeth in the case that there are two same elements for each value
		 * and n/2 different values. So find maximum length among anchored combs takes
		 * O(n/2 * n^2) = O(n^3) time.
		 * 
		 * So, in total, calculating the length of longest anchored comb takes O(n^3 +
		 * n^3) = O(n^3) time.
		 */

		// Create ArrayList to store the value and indexes of the same elements
		ArrayList<Integer> listOfSameElement = new ArrayList<Integer>();
		// Iterate through array
		for (int i = 0; i < n - 3; i++) {
			for (int j = n - 2; j > i + 1; j--) {
				if (A[i] == A[j] && A[i + 1] > A[i] && A[j + 1] > A[j]) {
					// Two teeth with the same first elements
					int k;
					// Check if that value already exits
					for (k = 0; k < listOfSameElement.size(); k += 3) {
						if (A[i] == listOfSameElement.get(k)) {
							// That value already exists
							// A longer comb with that value has already been considered
							break;
						}
					}
					if (k == listOfSameElement.size()) {
						// That value does not exist
						// Store value
						listOfSameElement.add(A[i]);
						// Store the index of first element of first tooth
						listOfSameElement.add(i);
						// Store the index of first element of last tooth
						listOfSameElement.add(j);
					}
				}
			}
		}

		int maxValue = 0;
		// Get index information from ArrayList
		for (int i = 0; i < listOfSameElement.size(); i += 3) {
			int left = listOfSameElement.get(i + 1);
			int right = listOfSameElement.get(i + 2);
			// Copy a sub-array
			int[] subA = Arrays.copyOfRange(A, left, right + 1);
			// Call LongestCombWithoutAnchored method to calculate length for each sub-array
			int value = LongestCombWithoutAnchored(subA, right - left + 1);
			// Compare with current maximum length
			if (value > maxValue) {
				// Assign the larger value to the maxValue
				maxValue = value;
			}
		}
		// ArrayList is empty if no two same elements can be the first element of tooth
		// Check if there is a comb with one tooth
		if (maxValue == 0) {
			for (int i = 0; i < n - 1; i++) {
				if (A[i] < A[i + 1]) {
					// If exists, return 1
					maxValue = 1;
					break;
				}
			}
		}

		// Return the value of the longest comb (>= 1) or 0 if there is no comb
		return maxValue;
	} // end of procedure LongestComb()

	public static int LongestCombWithoutAnchored(int[] A, int n) {
		// Define an array store the length of the longest comb ends at position 0 to n-1
		int[] L = new int[n - 1];
		// The length at position 1 is 1 because first two elements is a tooth
		L[1] = 1;
		// Fill in a dynamic programming table
		for (int i = 3; i < n - 1; i++) {
			if (A[i - 1] < A[i]) {
				// Is a tooth
				// Find the longest comb before position i-1 and this tooth can be attached to form a longer comb
				for (int j = i - 2; j > 0; j--) {
					if (A[j] > A[i - 1] && L[j] + 1 > L[i]) {
						// The second element of the previous tooth is larger than the first element of this tooth
						// Increase the longest comb that ends at j by one more value
						L[i] = L[j] + 1;
					}
				}
			}
		}

		int value = 0;
		// Find longest comb ends at position i and its value larger than first element of last tooth
		for (int i = 0; i < n - 1; i++) {
			if (L[i] > value && A[i] > A[n - 1]) {
				value = L[i];
			}
		}
		// Plus last tooth
		value = value + 1;

		// Return the value of the anchored comb
		return value;
	}

	public static int Computation(ArrayList<Integer> Instance, int opt) {
		// opt=1 here means option 1 as in -opt1, and opt=2 means option 2 as in -opt2
		int NGounp = 0;
		int size = 0;
		int Correct = 0;
		size = Instance.size();

		int[] A = new int[size - opt];
		// NGounp = Integer.parseInt((String)Instance.get(0));
		NGounp = Instance.get(0); // NOTE: NGounp = 0 always, as it is NOT used for any purpose
									// It is just the first "0" in the first line before instance starts.
		for (int i = opt; i < size; i++) {
			A[i - opt] = Instance.get(i);
		}
		int Size = A.length;
		if (NGounp > Size) {
			return (-1);
		} else {
			// Size
			int R = LongestComb(A, Size);
			return (R);
		}
	}

	public static String Test;

	public static void main(String[] args) {
		if (args.length == 0) {
			String msg = "Rerun with flag: " + "\n\t -opt1 to get input from dataOne.txt "
					+ "\n\t -opt2 to check results in dataTwo.txt";
			System.out.println(msg);
			return;
		}
		Test = args[0];
		int opt = 2;
		String pathname = "dataTwo.txt";
		if (Test.equals("-opt1")) {
			opt = 1;
			pathname = "dataOne.txt";
		}

		ArrayList<String> Datalist = new ArrayList<String>();
		Datalist = ReadData(pathname);
		ArrayList<ArrayList<Integer>> AIS = DataWash(Datalist);

		int Nins = AIS.size();
		int NGounp = 0;
		int size = 0;
		if (Test.equals("-opt1")) {
			for (int t = 0; t < Nins; t++) {
				int Correct = 0;
				int Result = 0;
				ArrayList<Integer> Instance = AIS.get(t);
				Result = Computation(Instance, opt);
				System.out.println(Result);
			}
		} else {
			int wrong_no = 0;
			int Correct = 0;
			int Result = 0;
			ArrayList<Integer> Wrong = new ArrayList<Integer>();
			for (int t = 0; t < Nins; t++) {
				ArrayList<Integer> Instance = AIS.get(t);
				Result = Computation(Instance, opt);
				System.out.println(Result);
				Correct = Instance.get(1);
				if (Correct != Result) {
					Wrong.add(t + 1);
					wrong_no = wrong_no + 1;
				}
			}
			if (Wrong.size() > 0) {
				System.out.println("Index of wrong instance(s):");
			}
			for (int j = 0; j < Wrong.size(); j++) {
				System.out.print(Wrong.get(j));
				System.out.print(",");

				/*
				 * ArrayList Instance = (ArrayList)Wrong.get(j); for (int k = 0; k <
				 * Instance.size(); k++){ System.out.println(Instance.get(k)); }
				 */
			}
			System.out.println("");
			System.out.println("Percentage of correct answers:");
			System.out.println(((double) (Nins - wrong_no) / (double) Nins) * 100);
		}
	}

}
