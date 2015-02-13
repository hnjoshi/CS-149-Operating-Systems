import java.util.ArrayList;

/**
 * This class runs First job First Algorithm.
 * @author Kernal
 *
 */
public class FirstJobFirst {
	ArrayList<Process> processes;
	
	public FirstJobFirst(ArrayList<Process> p) {
		processes = p;
	}
	
	public ArrayList<Process> fcfs() {
		ArrayList<Process> result = new ArrayList<Process>();
		boolean isFull = false;
		int counter = 0, runTime = 0;
		int Total_Processes = processes.size();
		Process p = null;
				
		
		while(!isFull) {
			
			p = processes.get(counter);
			
			while(p.getRunTime() > 0) {
				result.add(p);
				p.setRunTime(p.getRunTime() - 1);
				runTime++;
			}
			counter++;
			
			if(runTime >= 100) {
				isFull = true;
			}
		}
		System.out.println("Total processes arrived = "+Total_Processes);
		System.out.println("Total processes served = "+(counter));
		System.out.println("Total processes runtime = "+runTime + " THIS WILL be same as runTime b/c we are using float that mistake was there");
		System.out.println("\nTotal runtime = "+runTime);
		System.out.println("Last Process served was "+ p.getName()+".\n");
		
		return result;
	}
}
