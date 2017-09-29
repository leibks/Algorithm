//student name: Bowen Lei
//NUID: 001693665

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueueP {

    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        ArrayList<Request> myRequests = new ArrayList<Request>();
		Scanner myScanner = new Scanner(System.in);
		while (myScanner.hasNextLine()) {
			myRequests.add(new Request(myScanner.nextLine()));
		}

		//requests that have been executed 
		ArrayList<Request> result = new ArrayList<Request>();
		//requests that have been received 
		PriorityQueue<Request> prepare = new PriorityQueue<Request>();
		for (Request r : myRequests) {
			if (r.cancel) {
				//cancel this request
				prepare.remove(r);
			}
			else if (r.endMinute == 0) {
				//a single 24-hour time
				int startS = r.getStartMinute();
				while (prepare.size() != 0 ) {
              if (prepare.peek().getStartMinute() <= startS) {
              //check if this request is overlap with the 
					//any requests in the result
					boolean check = true;
					for (Request r2: result) {
						if(r2.overlaps(prepare.peek())) {
							check = false;
						}
					}
					if(check) {
                //if the first one did not overlap, add it and delete it
                //because before the single line time request we do not need 
                // them in the priority queue now
						result.add(prepare.peek());
						prepare.poll();
					}
              else {
                  //overlap and also delete it
                 prepare.poll(); 
              }
              }
              else {
                  //if the first one does not satisfy the single line time
                  //that means requests after this request all cannot be picked up
                  //because they are not optimal for this single line time
                 break;
              }   
				}
			}
			else { // a common request
				prepare.add(r);
			
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
		private boolean cancel;

		public Request(String inputLine) {
			if (inputLine.contains("cancel") 
					&& inputLine.contains(",")) {
				String[] inputParts = inputLine.split(",");
				String[] cancelPart = inputParts[0].split(" ");
				cancel = true;
				startMinute = toMinutes(cancelPart[1]);
				endMinute = toMinutes(inputParts[1]);

			}
			else if (inputLine.contains(",")) {
				String[] inputParts = inputLine.split(",");
				cancel = false;
				startMinute = toMinutes(inputParts[0]);
				endMinute = toMinutes(inputParts[1]);
			}
			else {
				cancel = false;
				startMinute = toMinutes(inputLine);
				endMinute = 0;
			}

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

		public boolean equals(Object o) {
			if (!(o instanceof Request)) {
				return false;
			}
			Request that = (Request) o;
			return this.startMinute == that.startMinute &&
					this.endMinute == that.endMinute;
		}

    }
}