//student name: Bowen Lei
//NUID: 001693665

import java.util.Scanner;
import java.util.ArrayList;

public class CompanyGreedy {
	public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
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
		int[] help = new int[APrices.length];
		
		//make the the beginning company
		int curr = 0;
		double bestCost = Double.POSITIVE_INFINITY;
		for (int i = 0; i < 3; i++) {
			if (dailyCosts[i][0] < bestCost) {
           bestCost = dailyCosts[i][0];
				curr = i;
			}
		}
		help[0] = curr;
        
		for (int i = 1; i < APrices.length; i++) {
			int now = 0;
			double cost = Double.POSITIVE_INFINITY;
			for (int j = 0; j < 3; j++) {
				int jCost = 0;
				if (help[i - 1] == j) {
					jCost = dailyCosts[j][i];
				}
				else {
					jCost = dailyCosts[j][i] + changeCosts[j];
				}
				if (jCost < cost) {
              cost = jCost;
					now = j;
				}
			}
			help[i] = now;
		}
		
		for (int i : help) {
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

		for (String r : result) {
			System.out.println(r);
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