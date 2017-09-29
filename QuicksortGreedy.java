//student name: Bowen Lei
//NUID: 001693665

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;


// Solving the interval scheduling problem with greedy algorithm 
// Constant time.
public class QuicksortGreedy {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ArrayList<Request> myRequests = new ArrayList<Request>();
		Scanner myScanner = new Scanner(System.in);
		while (myScanner.hasNextLine()) {
			myRequests.add(new Request(myScanner.nextLine()));
		}

		//Use an ArrayList of Requests, and simply call it by 
		//using quickSort greedy method
		Collections.sort(myRequests);

		ArrayList<Request> result = new ArrayList<Request>();
		result.add(myRequests.get(0));
		// pick up the not-overlapping interval time basing on greedy method
		int j = 0;
		for(int i = 1; i < myRequests.size(); i++) {
			if (!myRequests.get((j)).overlaps(myRequests.get(i))) {
				result.add(myRequests.get(i));
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