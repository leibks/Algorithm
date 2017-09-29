//student name: Bowen Lei
//NUID: 001693665

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class DivideAndConquer {

	public static void main(String[] args) {
		/* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
		long start = System.currentTimeMillis();
		ArrayList<Request> myRequests = new ArrayList<Request>();
		Scanner myScanner = new Scanner(System.in);
		while (myScanner.hasNextLine()) {
			myRequests.add(new Request(myScanner.nextLine()));
		}

		//requests that will be shown
		ArrayList<String> result = new ArrayList<String>();
		//represent A B C three companies'changeover fees 
		int[] changeCosts = new int[]{myRequests.get(0).getListValue()[0]
				, myRequests.get(0).getListValue()[1], myRequests.get(0).getListValue()[2]};

		//represent A B C three companies' per day charge
		int[] APrices = myRequests.get(1).getListValue();
		int[] BPrices = myRequests.get(2).getListValue();
		int[] CPrices = myRequests.get(3).getListValue();

		//0 represents company A, 1 represents company B, 2 represents C company,
		//3 represents any company in A, B and C.
		int[][] dailyCosts = new int[][]{APrices, BPrices, CPrices};
		int[] prepareResult = new int[0];
		//base case
		if (APrices.length == 1) {
			int curr = 0;
			double bestCost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < 3; i++) {
				if (dailyCosts[i][0] < bestCost) {
					curr = i;
				}
			}
			prepareResult[0] = curr;
		}
		else { // divide and conquer part
			prepareResult = getBestSchedule(changeCosts, dailyCosts, 3, 3, 0, APrices.length - 1).getList();
		}
		//transfer the result
		for (int i : prepareResult) {
			if (i == 0) {
				result.add("A");
			}
			else if (i == 1) {
				result.add("B");
			}
			else {
				result.add("C");
			}
		}
		//output the results
		for (String r : result) {
			System.out.println(r);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time" + (end-start));
	}

	//get the best schedule from starting company to ending company
	//from index start to index end
	static Schedule getBestSchedule(int[] changeCosts, int[][] dailyCosts,
			int startCompany, int endCompany, int startIndex, int endIndex) {
		//only one company situation
		if (startIndex == endIndex)  {
			if ((startCompany != 3) && (endCompany != 3) && (startCompany != endCompany)) {
				// not available company, start company not equal end company
				//return unavailable schedule, make the cost become biggest 
				return new Schedule( Integer.MAX_VALUE
						, new int[]{startCompany});
			}
			else {
				//it is available and has been chosen, start and end company do not conflict,
				//choose the available one
				if (startCompany != 3) {
					return new Schedule(dailyCosts[startCompany][startIndex]
							, new int[]{startCompany});
				}
				else {
					return new Schedule(dailyCosts[endCompany][endIndex]
							, new int[]{endCompany});
				}
			}
		}

		//left part last index and right part first index
		int lLast = (startIndex + endIndex) / 2;
		int rFirst= lLast + 1;

		Schedule endAList = getBestSchedule(changeCosts, dailyCosts, startCompany
				, 0, startIndex, lLast);
		Schedule endBList = getBestSchedule(changeCosts, dailyCosts, startCompany
				, 1, startIndex, lLast);
		Schedule endCList = getBestSchedule(changeCosts, dailyCosts, startCompany
				, 2, startIndex, lLast);
		Schedule startAList = getBestSchedule(changeCosts, dailyCosts, 0
				, endCompany, rFirst, endIndex);
		Schedule startBList = getBestSchedule(changeCosts, dailyCosts, 1
				, endCompany, rFirst, endIndex);
		Schedule startCList = getBestSchedule(changeCosts, dailyCosts, 2
				, endCompany, rFirst, endIndex);

		//best choice for last company of left part and 
		//best choice for first company of right part 
		int bestEnd = 0;
		int bestStart = 0;
		//total cost of the left half and right half cost
		int bestCost = Integer.MAX_VALUE;

		//store three companies' (decide the end company) schedule
		Schedule[] endArray = new Schedule[]{endAList, endBList, endCList};
		//store three companies' (decide the start company) schedule list
		Schedule[] startArray = new Schedule[]{startAList, startBList, startCList};

		//compare 3 * 3 possibilities and get the best one
		//choose the end company of left part
		for (int i = 0; i < 3; i++) {
			//choose start company of right part
			for (int j = 0; j < 3; j++) {
				//now combine choice situation cost
				int loopCost = 0; 
				// if any one of choice is max, which means this match cannot be selected
				// because the one of end or start have been decided 
				if ((endArray[i].getCost() == Integer.MAX_VALUE) 
						|| (startArray[j].getCost() == Integer.MAX_VALUE) ) {
					continue;
				}
				if (i == j) {
					loopCost = endArray[i].getCost() + startArray[j].getCost();
				}
				else {
					loopCost = endArray[i].getCost() + startArray[j].getCost()
							+ changeCosts[j];
				}
				if ( loopCost < bestCost) {
					bestCost = loopCost;
					bestEnd = i;
					bestStart = j;
				}	
			}
		}

		return new Schedule( bestCost
				, combineArray(endArray[bestEnd].getList(), startArray[bestStart].getList()));

	}

	//combine two integers array lists to one list
	static int[] combineArray(int[] array1, int[] array2) {
		int[] result = new int[array1.length + array2.length];
		for (int i = 0; i < array1.length; i++) {
			result[i] = array1[i];
		}
		for (int j = array1.length; j < array1.length + array2.length; j++) {
			result[j] = array2[j - array1.length];
		}
		return result;
	}

	// represent the schedule that contains the list of companies and sum of costs 
	private static class Schedule {
		private int cost;
		private int[] list;

		public Schedule(int cost, int[] list) {
			this.cost = cost;
			this.list = list;
		}

		public int getCost() {
			return cost;
		}

		public int[] getList() {
			return list;
		}
	}

	// Request can be one of changeover fee of three companies or per company prices for each day we need 
	private static class Request {
		private String company;
		private int[] listPrice;

		public Request(String inputLine) {
			if (inputLine.contains(":")) {
				String[] inputParts = inputLine.split(":");
				listPrice = transferPrice(inputParts[1]);
				company = inputParts[0];
			}
			else {
				listPrice = transferPrice(inputLine);
				company = "AllCompanies";
			}
		}

		//transfer one string to a list of prices we for every day or for every company
		private int[] transferPrice(String string) {
			String[] prices = string.split(",");
			int[] result = new int[prices.length];
			for (int i = 0; i < prices.length; i++) {
				result[i] = toInteger(prices[i]);
			}

			return result;
		}

		//transfer the string to integer
		private int toInteger(String s) {
			return new Integer(s);
		}

		//get the prices of this company
		public int[] getListValue() {
			return listPrice;
		}

	}
}