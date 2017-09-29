//student name: Bowen Lei
//NUID: 001693665

import java.util.ArrayList;
import java.util.Scanner;

public class DynamicProgramming {

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

		//optimal schedule up to day i and the end company is A ... 0 to 100 day (101)
		Schedule[] endCompanyA = new Schedule[APrices.length + 1];
		//optimal schedule up to day i and the end company is B
		Schedule[] endCompanyB = new Schedule[APrices.length + 1];
		//optimal schedule up to day i and the end company is C
		Schedule[] endCompanyC = new Schedule[APrices.length + 1];
		//three companies schedule list -- easy for loop
		Schedule[][] arrayCompany = new Schedule[][]{endCompanyA, endCompanyB, endCompanyC};

		//base case :
		endCompanyA[0] = new Schedule(0, new int[]{3});
		endCompanyB[0] = new Schedule(0, new int[]{3});
		endCompanyC[0] = new Schedule(0, new int[]{3});

		for (int k = 1; k <= APrices.length; k++) {
			//for switchTo company choices, end this i company array 
			for (int i = 0; i < 3; i++) {
				// for find the optimal choice for "swithTo" company end, 
				// add it to end(i company) array[k day]
				int bestValue = Integer.MAX_VALUE;
				int choice = 0;
				//for from company choices
				for (int j = 0; j < 3; j++) {
					int nowValue = 0;
					if ((i == j) || (k == 1)) {
						nowValue = arrayCompany[j][k - 1].getCost() + dailyCosts[i][k - 1];
					}
					else {
						nowValue = arrayCompany[j][k - 1].getCost() + dailyCosts[i][k - 1]
								+ changeCosts[i];
					}
					if (nowValue <= bestValue) {
						bestValue = nowValue;
						choice = j;
					}
				}
				arrayCompany[i][k] = new Schedule(bestValue
						, addArray(arrayCompany[choice][k - 1].getList() , i));
				//save the memory 
				if (k > 2) {
					arrayCompany[i][k - 2] = new Schedule(0
							, new int[]{0});
				}

			}
		}

		//finally find the optimal
		int finalCost = Integer.MAX_VALUE;
		int finalEnd = 0;
		for (int i = 0; i < 3; i++) {
			if (arrayCompany[i][APrices.length].getCost() < finalCost) {
				finalEnd = i;
				finalCost = arrayCompany[i][APrices.length].getCost();
			}
		}

		//requests that will be shown
		int[] prepare = arrayCompany[finalEnd][APrices.length].getList();

		for (int i : prepare) {
			if (i == 0) {
				result.add("A");
			}
			else if (i == 1) {
				result.add("B");
			}
			else if (i == 2){
				result.add("C");
			}
		}

		for (String r : result) {
			System.out.println(r);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time" + (end-start));
	}

	//combine two integers array lists to one list
	static int[] addArray(int[] array1, int add) {
		int[] result = new int[array1.length + 1];
		for (int i = 0; i < array1.length; i++) {
			result[i] = array1[i];
		}
		result[array1.length] = add;
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

