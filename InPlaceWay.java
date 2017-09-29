//student name: Bowen Lei
//NUID: 001693665


import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class InPlaceWay {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ArrayList<String> prepare = new ArrayList<String>();
		Scanner myScanner = new Scanner(System.in);
		while (myScanner.hasNextLine()) {
			prepare.add(myScanner.nextLine());
		}
		//show in the final result
		ArrayList<Integer> result = new ArrayList<Integer>();
		//represent the size of a sliding window
		int windows = new Integer(prepare.get(0).split("=")[1]);

		//Represent the total numbers we want to find the media
		int[] nums = new int[prepare.size() - 1];
		for (int i = 1; i < prepare.size(); i ++ ) {
			nums[i - 1] = (new Integer(prepare.get(i)));
		}

		//get every series of numbers medium
		for (int j = 0; j <= nums.length - windows; j++ ) {
			int[] listWant = new int[windows];
			for (int k = j; k < j + windows; k ++) {
				listWant[k - j]= nums[k];
			}
			result.add(getMedium(listWant, (listWant.length / 2),  0, windows - 1));
		}

		for (int r : result) {
			System.out.println(r);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time" + (end-start));

	}
	//choose the random int from the given list during the bound between min and max
	//		static int setSeed(int[] given, int min, int max) {
	//			int index = new Random().nextInt(max - min + 1) + min;
	//			int pivot = given[index];
	//			return pivot;
	//		}

	//use the select procedure to get the medium of the array integers 
	static int getMedium(int[] given, int k, int min, int max) {
		int beginEqual = min;
		int beginGreater = min;
		int index = new Random().nextInt(max - min + 1) + min;
		int pivot = given[index];
		//	      int temp = given[given.length - 1];
		//	      given[given.length - 1] = pivot;
		//	      given[index] = temp;

		for (int i = min; i <= max; i++) {
			if (given[i] < pivot ) {
				if (beginEqual == beginGreater) {
					int temp3 = given[i];
					given[i] = given[beginGreater];
					given[beginGreater] = temp3;
					beginEqual++;
					beginGreater++;
				}
				else {
					int tem = given[i];
					given[i] = given[beginEqual];
					given[beginEqual] = tem; 

					int temp2 = given[beginGreater];
					given[beginGreater] = given[i];
					given[i] = temp2;

					beginGreater++;
				}
			}
			else if (given[i] == pivot) {
				int tp = given[i];
				given[i] = given[beginGreater];
				given[beginGreater] = tp;
				beginGreater++;
			}
		}
		if (k < beginEqual) {
			return getMedium(given, k, min, beginEqual - 1);
		}
		else if (k < beginGreater) {
			return pivot;
		}
		else {
			return getMedium(given, k, beginGreater, max);
		}
	}
}

