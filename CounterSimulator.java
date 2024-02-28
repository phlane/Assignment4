/**
 * 
 */
package accidentpack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayDeque;

/**
 * Simulate
 */
public class CounterSimulator {
	final int MINUTES_IN_DAY = 1440;

	ArrayList<ArrayDeque<Report>> days;
	String topOfLastQueue;

	/**
	 * Pre: queue sorted early -> late, contains just reports in county, has at
	 * least 1 report
	 * 
	 * @param accidents
	 */
	public CounterSimulator(ArrayDeque<Report> accidents) {
		days = new ArrayList<>();
		ArrayDeque<Report> day;

		while (!accidents.isEmpty()) {
			day = new ArrayDeque<>();
			day.add(accidents.poll());
			while (accidents.peek() != null && accidents.peek().getDate().equals(day.peek().getDate())) {
				day.add(accidents.poll());
			}
			days.add(day);
		}

	}

	/**
	 * Acts as the operator for the simulateDay method and executes simulateDay for
	 * as many days there are in the array.
	 * 
	 * @param counterNumber
	 */
	public void run(int counterNumber) {
		int[] results = new int[days.size()];

		ArrayDeque<Report> reportQueue = days.get(0).clone();
		// topOfLastQueue = reportQueue.peek().getDate().toString();
		for (int i = 1; i < days.size(); i++) {

			results[i] = simulateDay(reportQueue, counterNumber);
			if (results[0] == -1) {
				counterNumber += 1;
			} else {
				// System.out.println("enough");
			}

			for (Report report : days.get(i)) {
				// System.out.println("Here");
				reportQueue.add(report);
				// System.out.println(reportQueue.size());

			}
		}
	}

	/**
	 * Accepts a queue of days and the number of "counters" that are specified to
	 * work on that day. It iterates through the day, assigning reports to counters
	 * as they become free, and records any left over records of the day.
	 * 
	 * @param queue
	 * @param counterNumber
	 * @return
	 */

	private int simulateDay(ArrayDeque<Report> queue, int counterNumber) {
		CounterArray counters = new CounterArray(counterNumber);
		Report r;
		topOfLastQueue = queue.peek().getDate().toString();
		int time = 0, timeDiff;
		int totalWorkTime = counterNumber * 24 * 60;

		while (totalWorkTime > 0) {
			r = queue.poll();
			if (Integer.parseInt(r.getDate().toString().replace("-", ""))
					- Integer.parseInt(topOfLastQueue.replace("-", "")) >= 2) {
				System.out.println("overflow");
				return -1;
			}

			// timeDiff = r.getTime() - time;
			counters.forwardBy(60);
			counters.addToCounter(r);
			time = r.getTime();
		}

		if (time < MINUTES_IN_DAY) {
			counters.forwardBy(MINUTES_IN_DAY - time);
		}

		if (queue.peek() == null) {
			return 1;
		} else {
			topOfLastQueue = queue.peek().toString();
			return 1;
		}

	}

	private static int testForCounters() {

	}

	// private int simulateDay(ArrayDeque<Report> queue, int counterNumber) {
//		boolean DEBUG_PRINT_STATE = false;
//		CounterArray counters = new CounterArray(counterNumber);
//		Report r;
//		int time = 0, timeDiff;
//
//		if (DEBUG_PRINT_STATE)
//			System.out.printf("t%d - %s\n", time, counters.printState());
//
//		while ((r = queue.poll()) != null) {
//			timeDiff = r.getTime() - time;
//			counters.forwardBy(timeDiff);
//			counters.addToCounter(r);
//			time = r.getTime();
//			if (DEBUG_PRINT_STATE)
//				System.out.printf("added: %s %d sev: %d \nt%d - %s\n", r.getId(), r.getTime(), r.getSeverity(), time,
//						counters.printState());
//		}
//
//		if (time < MINUTES_IN_DAY) {
//			counters.forwardBy(MINUTES_IN_DAY - time);
//			if (DEBUG_PRINT_STATE)
//				System.out.printf("t%d - %s\n", MINUTES_IN_DAY, counters.printState());
//		}
//
//		return counters.getFreeCounters();
//	}

	public ArrayList<ArrayDeque<Report>> getQueues() {
		return days;
	}

	/**
	 * Main method that calls the CSV File Iterator, forms a report, sorts the array
	 * by start time of the reports, and creates the 5 lists of sorted counties.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String args[]) throws FileNotFoundException, IOException {
		ArrayDeque<Report> reportCA = new ArrayDeque<>();
		ArrayDeque<Report> reportFL = new ArrayDeque<>();
		ArrayDeque<Report> reportTX = new ArrayDeque<>();
		ArrayDeque<Report> reportOH = new ArrayDeque<>();
		ArrayDeque<Report> reportDE = new ArrayDeque<>();
		ArrayList<Report> reportList = new ArrayList<>();

		Iterator<String[]> iter = new CSVFileIterator("src\\accidentpack\\accidents.csv");
		while (iter.hasNext()) {
			Report report = new Report(iter.next());
			reportList.add(report);
		}
//		
//		while (!q.isEmpty()) {
//			System.out.println(q.pop().toString());
//		}

		Comparator<Report> byStartTime = (s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime());
		Collections.sort(reportList, byStartTime);

		for (int i = 0; i < reportList.size(); i++) {
			if (reportList.get(i).getState().equals("CA") && reportList.get(i).getCounty().equals("Los Angeles")) {
				reportCA.add(reportList.get(i));
			} else if (reportList.get(i).getState().equals("FL") && reportList.get(i).getCounty().equals("Orange")) {
				reportFL.add(reportList.get(i));
			} else if (reportList.get(i).getState().equals("TX") && reportList.get(i).getCounty().equals("Harris")) {
				reportTX.add(reportList.get(i));
			} else if (reportList.get(i).getState().equals("OH") && reportList.get(i).getCounty().equals("Hamilton")) {
				reportOH.add(reportList.get(i));
			} else if (reportList.get(i).getState().equals("DE")
					&& reportList.get(i).getCounty().equals("New Castle")) {
				reportDE.add(reportList.get(i));
			} // - Phil
		}

		// List<Report> reportList = new ArrayList<>(reportCA);

		int CAResult = testForCounters();
		int FLResult = testForCounters();
		int TXResult = testForCounters();
		int OHResult = testForCounters();
		int DEResult = testForCounters();

		ArrayList<ArrayDeque<Report>> testQueues = testCA.getQueues();

		System.out.println("Length:" + testQueues.size());

//		for (ArrayDeque<Report> n : testQueues) {
//			System.out.println("[ ");
//			for (Report r : n) {
//				System.out.println(r.toString());
//			}
//			System.out.println("] ");
//		}
//		System.out.println("\n");

		testCA.run(1);
//		test.run(2);
//		test.run(3);

	}
}
