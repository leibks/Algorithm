//student name: Bowen Lei
//NUID: 001693665

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class SelectProcedure {

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
			result.add(getMedium(listWant, (listWant.size() / 2) ));
		}

		for (int r : result) {
			System.out.println(r);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time" + (end-start));

	}

	//use the select procedure to get the medium of the array integers 
	static int getMedium(ArrayList<Integer> given, int medium) {
	      int index = new Random().nextInt(given.size());
		   int pivot = given.get(index);
			ArrayList<Integer> sMinus = new ArrayList<Integer>();
			ArrayList<Integer> sPlus = new ArrayList<Integer>();
			for (int i = 0; i < given.size(); i++ ) {
	         if (i != index) {
				if (given.get(i) < pivot) {
					sMinus.add(given.get(i));
				}
				else {
					sPlus.add(given.get(i));
				}
			}
	      }
			if (sMinus.size() == medium ) {
				return pivot;
			}
			else if (sMinus.size() > medium ) {
				return getMedium(sMinus, medium);
			}
			else {
				return getMedium(sPlus, medium - (1 + sMinus.size()));
			}
		}

}

