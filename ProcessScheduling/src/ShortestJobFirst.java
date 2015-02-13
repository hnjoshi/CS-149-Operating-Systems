import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class runs Shortest job First Algorithm.
 * @author Kernal
 */
public class ShortestJobFirst {
	
	ArrayList<Process> processes; 
	public ShortestJobFirst(ArrayList<Process> p) {
		processes = p;
		
		//Sort based on Runtime, if the arraival time is same. 
		Collections.sort(processes, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				int comp = Float.compare(p1.getArrivalTime(), p2.getArrivalTime());
				if(comp == 0) {
					comp = Float.compare(p1.getRunTime(), p2.getRunTime());
				}
				return comp;
			}			
		}); 
	}
	
	//This function performs the Shortest Job First Algorithm. 
	public ArrayList<Process> sjf() {
		ArrayList<Process> result = new ArrayList<Process>();
		
		//VADIL your work goes here! 
		
		
		return result;
	}
}
