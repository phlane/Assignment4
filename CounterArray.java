/**
 * 
 */
package accidentpack;

/**
 * 
 */
public class CounterArray {
	

	class Counter {
		final static int SEVERITY_MULTIPLIER = 60;

		private int time;
			
		/**
		 * Constructor for counter class.
		 */
		Counter() {
			time = 0;
		}
		
		/**
		 * Subtracts a specifiec time value from the time of the counter
		 * @param m
		 */
		void minus(int m) {
			if (m > time) {
				time = 0;
			} else {
				time -= m;
			}
		}
			
		/**
		 * Adds the amount of time based on the severity of the crash to the counter
		 * @param r
		 */
		void process(Report r) {
			time += r.getSeverity() * SEVERITY_MULTIPLIER;
		}
			
		int timeRemaining() {
			return time;
		}
	}
	
	Counter[] counters;
	
	/**
	 * Creates a list of counters based on a specific number
	 * @param counterNumber
	 */
	public CounterArray(int counterNumber) {
		counters = new Counter[counterNumber];
		for (int i = 0; i < counterNumber; i++) {
			counters[i] = new Counter();
		}
	}
	
	/**
	 * Advances the time of each counter by a specific amount of minutes
	 * @param minutes
	 */
	public void forwardBy(int minutes) {
		for (Counter c : counters) {
			c.minus(minutes);
		}
	}
	
	/**
	 * Finds a free counter and assigns the record to that counter.
	 * @param r
	 */
	public void addToCounter(Report r) {
		int min = 0, minTime = counters[0].time;
		for (int i = 0; i < counters.length; i++) {
			 if (counters[i].time < minTime) {
				 min = i;
				 minTime = counters[i].time;
			 }
		}
		counters[min].process(r);
	}
	
	/**
	 * Returns the number of free counters
	 * @return
	 */
	public int getFreeCounters() {
		int free = 0;
		for (Counter c : counters) {
			if (c.timeRemaining() == 0) {
				free++;
			}
		}
		return free;
	}
	
	/**
	 * Return the total number of minutes left for all timers
	 * @return
	 */
	public int getMinutesLeft() {
		int minutesLeft = 0;
		for (Counter c : counters) {
			minutesLeft += c.timeRemaining();
		}
		return minutesLeft;
	}
	
	/**
	 * Prints the current state of each counter
	 * @return
	 */
	public String printState() {
		String returnString = "[";
		for (Counter c : counters) {
			returnString += c.time + ",";
		}
		returnString += "]";
		return returnString;
	}
}
