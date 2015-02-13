
/***
 * An arrival time: a float value from 0 through 99 (measured in quanta).
• An expected total run time: a float value from 0.1 through 10 quanta.
• A priority: integer 1, 2, 3, or 4 (1 is highest)
 * @author Kernal
 *
 */
public class Process {
	private char name;
	private float arrivalTime;
	private float runTime;
	private int priority;
	
	public Process(float aTime, float rTime, int priority) {
		this.arrivalTime = aTime;
		this.runTime = rTime;
		this.priority = priority;
	}
	
	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	public float getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(float arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public float getRunTime() {
		return runTime;
	}

	public void setRunTime(float runTime) {
		this.runTime = runTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String toString() {
		return name + "\t|\t" + String.format("%2.2f", arrivalTime) + "\t|\t" + String.format("%2.2f", runTime) + "\t|\t" + String.format("%d", priority);
	}
}
