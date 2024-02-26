/**
 * 
 */
package accidentpack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayDeque;

/**
 * Simulate 
 */
public class CounterSimulator {
	
	ArrayList<ArrayDeque<Report>> days;

	/**
	 * Pre: queue sorted early -> late, contains just reports in county, has at least 1 report
	 * @param accidents
	 */
	public CounterSimulator(ArrayDeque<Report> accidents) {
		days = new ArrayList<>();
		ArrayDeque<Report> day;

		while (! accidents.isEmpty()) {
			day = new ArrayDeque<>();
			day.add(accidents.poll());
			while (accidents.peek() != null &&
					accidents.peek().getDate().equals(day.peek().getDate())) {
				day.add(accidents.poll());
			}
			days.add(day);
		}
		
	}
	
	public void run(int counterNumber) {
		int[] results = new int[days.size()];
		
		for (int i = 0; i < days.size(); i++) {
			results[i] = simulateDay(days.get(i).clone(), counterNumber);
		}
		
	}
	
	private int simulateDay(ArrayDeque<Report> queue, int counterNumber) {
		boolean DEBUG_PRINT_STATE = true;
		CounterArray counters = new CounterArray(counterNumber);
		Report r;
		int time = 0, timeDiff;
		
		if (DEBUG_PRINT_STATE) System.out.printf("t%d - %s\n", time, counters.printState());
		
		while ((r = queue.poll()) != null) {
			timeDiff = r.getTime() - time;
			counters.forwardBy(timeDiff);
			counters.addToCounter(r);
			time = r.getTime();
			if (DEBUG_PRINT_STATE) System.out.printf("added: %s %d sev: %d \nt%d - %s\n", r.getId(), r.getTime(), r.getSeverity(), time, counters.printState());
		}

		if (time < 7200) {
			counters.forwardBy(7200 - time);
			if (DEBUG_PRINT_STATE) System.out.printf("t%d - %s\n", 7200, counters.printState());
		}

		return counters.getFreeCounters();
	}

	public ArrayList<ArrayDeque<Report>> getQueues() {
		return days;
	}
	
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		ArrayDeque<Report> q = new ArrayDeque<>();
		Iterator<String[]> iter = new CSVFileIterator("resources/acc-test-county.csv");
		while (iter.hasNext()) {
			q.add(new Report(iter.next()));
		}
//		
//		while (!q.isEmpty()) {
//			System.out.println(q.pop().toString());
//		}
		CounterSimulator test = new CounterSimulator(q);
		
		ArrayList<ArrayDeque<Report>> testQueues = test.getQueues();

		System.out.println("Length:" + testQueues.size());
		
		for (ArrayDeque<Report> n : testQueues) {
			System.out.println("[ ");
			for (Report r : n) {
				System.out.println(r.toString());
			}
			System.out.println("] ");
		}
		System.out.println("\n");
		
		test.run(1);
		test.run(2);
		test.run(3);
		
	}
}
