//student name: Bowen Lei
//NUID: 001693665

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

// Solving the interval scheduling problem with greedy algorithm 
// Constant time.
public class CountinSortGreedy {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ArrayList<Request> myRequests = new ArrayList<Request>();
		Scanner myScanner = new Scanner(System.in);
		while (myScanner.hasNextLine()) {
			myRequests.add(new Request(myScanner.nextLine()));
		}

		//Suppose we want to sort N integers(ending time) that are in the range [0, 24*60],
		//which is range of ending time
		Request[] countSort = new Request[24*60];	
		//initialize countSort all to (00,00) request time
		for (int k = 0; k < countSort.length; k++ ) {
			countSort[k] = new Request("00:00,00:00");
		}

		for (int i = 0; i < myRequests.size(); i++) {
			int endTime = myRequests.get(i).getEndMinute();
			if (countSort[endTime].compareTo(new Request("00:00,00:00")) == 0) {
				countSort[endTime] = myRequests.get(i);
			}
			else if (countSort[endTime].getStartMinute() < myRequests.get(i).getStartMinute()) {
					countSort[endTime] = myRequests.get(i);
			}
		}

		ArrayList<Request> result = new ArrayList<Request>();
		// pick up the not-overlapping interval time basing on greedy method
		int j = 0;
		//find the earliest end time request
		for(int h = 0; h < countSort.length; h++) {
			if (!(countSort[h].compareTo(new Request("00:00,00:00")) == 0)) {
				j = h;
				break;
			}
		}
		result.add(countSort[j]);
		for(int i = j + 1; i < countSort.length; i++) {
			if (!(countSort[i].compareTo(new Request("00:00,00:00")) == 0) 
					&& !(countSort[j].overlaps(countSort[i]))) {
				result.add(countSort[i]);
				j = i;
			}
		}

		for (Request r : result) {
			System.out.println(r);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time" + (end-start));
	}


	  // Request class would normally be a separate file, but HackerRank wants
    // a single file for submission.
	private static class Request implements Comparable {
		private int startMinute;
		private int endMinute;

		public Request(String inputLine) {
			String[] inputParts = inputLine.split(",");
			startMinute = toMinutes(inputParts[0]);
			endMinute = toMinutes(inputParts[1]);
		}

		private static int toMinutes(String time) {
			String[] timeParts = time.split(":");
			int hour = new Integer(timeParts[0]);
			int minute = new Integer(timeParts[1]);
			return hour*60 + minute;
		}

		public int getStartMinute() {
			return startMinute;
		}

		public int getEndMinute() {
			return endMinute;
		}

		public String toString() {
			return timeToString(startMinute) + "," + timeToString(endMinute);
		}

		private static String timeToString(int minutes) {
			if ((minutes % 60) < 10) {
				return (minutes/60) + ":0" + (minutes%60);
			}
			return (minutes/60) + ":" + (minutes%60);
		}

		public boolean overlaps(Request r) {
			// Four kinds of overlap...
			// r starts during this request:
			if (r.getStartMinute() >= getStartMinute() && 
				r.getStartMinute() < getEndMinute()) {
				return true;
			}
			// r ends during this request:
			if (r.getEndMinute() >= getStartMinute() &&
				r.getEndMinute() < getEndMinute()) {
				return true;
			}
			// r contains this request:
			if (r.getStartMinute() <= getStartMinute() &&
				r.getEndMinute() >= getEndMinute()) {
				return true;
			}
			// this request contains r:
			if (r.getStartMinute() >= getStartMinute() &&
				r.getEndMinute() <= getEndMinute()) {  
				return true;
			}
			return false;
		}

		// Allows use of Collections.sort() on this object
		// (implements Comparable interface)
		public int compareTo(Object o) {
			if (!(o instanceof Request)) {
				throw new ClassCastException();
			}
			Request r = (Request)o;
			if (r.getEndMinute() > getEndMinute()) {
				return -1;
			} else if (r.getEndMinute() < getEndMinute()) {
				return 1;
			} else if (r.getStartMinute() < getStartMinute()) {
				// Prefer later start times, so sort these first
				return -1;
			} else if (r.getStartMinute() > getStartMinute()) {
				return 1;
			} else {
				return 0;
			}
		}

	}

}