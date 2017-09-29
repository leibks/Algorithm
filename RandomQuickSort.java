//student name: Bowen Lei
//NUID: 001693665

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class RandomQuickSort {

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
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 1; i < prepare.size(); i ++ ) {
			nums.add(new Integer(prepare.get(i)));
		}
		
		//get every series of numbers medium
		for (int j = 0; j <= nums.size() - windows; j++ ) {
			ArrayList<Integer> listWant = new ArrayList<Integer>();
			for (int k = j; k < j + windows; k ++) {
				listWant.add(nums.get(k));
			}
			Collections.sort(listWant);
			result.add(listWant.get(listWant.size() / 2 ));
		}

		for (int r : result) {
			System.out.println(r);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time" + (end-start));

	}


}

