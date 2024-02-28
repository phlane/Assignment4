package accidentpack;

/**
 * Class implements a timer for measuring task duration
 *
 * Setters are not implemented as time is only set by startTimer and endTimer
 * Changed to no longer print timer, returns timer duration in seconds instead
 * @author Charles Winkelman
 */
public class Timer{

	private long timerStartTime;
	private long timerEndTime;

    /**
     * General Timer constructor
     */
    public Timer(){};

    /**
     * Sets timer start to now
     */
	public void start() {
		timerStartTime = System.currentTimeMillis();
	}

    /**
     * Sets timer end to now
     */
	public void stop() {
      timerEndTime = System.currentTimeMillis();
  }

    /**
     * Returns timer duration in seconds
     * Pre: started and stopped timer
     *
     * @return timer end - timer start / 1000
     */
  public double getTime() {
      return ((double) (timerEndTime - timerStartTime)) / 1000;
    }

    /**
     * Get start time
     * @return start time
     */
	public long getStartTime() { return timerStartTime; }

    /**
     * Get end time
     * @return end time
     */
	public long getEndTime() { return timerEndTime; }

}
