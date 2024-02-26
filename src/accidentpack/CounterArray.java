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
			
		Counter() {
			time = 0;
		}
		
		void minus(int m) {
			if (m > time) {
				time = 0;
			} else {
				time -= m;
			}
		}
			
		void process(Report r) {
			time += r.getSeverity() * SEVERITY_MULTIPLIER;
		}
			
		int timeRemaining() {
			return time;
		}
	}
	
	Counter[] counters;
	
	public CounterArray(int counterNumber) {
		counters = new Counter[counterNumber];
		for (int i = 0; i < counterNumber; i++) {
			counters[i] = new Counter();
		}
	}
	
	public void forwardBy(int minutes) {
		for (Counter c : counters) {
			c.minus(minutes);
		}
	}
	
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
	
	public int getFreeCounters() {
		int free = 0;
		for (Counter c : counters) {
			if (c.timeRemaining() == 0) {
				free++;
			}
		}
		return free;
	}
	
	public String printState() {
		String returnString = "[";
		for (Counter c : counters) {
			returnString += c.time + ",";
		}
		returnString += "]";
		return returnString;
	}
}
