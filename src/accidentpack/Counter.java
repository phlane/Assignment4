/**
 * 
 */
package accidentpack;

/**
 * 
 */
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