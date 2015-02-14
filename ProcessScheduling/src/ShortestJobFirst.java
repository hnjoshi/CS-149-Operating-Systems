import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class runs Shortest job First Algorithm.
 * @author Kernel
 */
public class ShortestJobFirst {
	
	Process p,p2 = null;
	int counter = 0, runTime = 0, holdOn = 2;
	
	ArrayList<Process> processes; 
	public ShortestJobFirst(ArrayList<Process> p) {
		processes = p;
	}
	
	//This function performs the Shortest Job First Algorithm. 
	public ArrayList<Process> sjf() {
		ArrayList<Process> result = new ArrayList<Process>();
		int Total_Processes = processes.size();
		
		for(Process p : processes) {
			System.out.println(p);
		}
		
		boolean isFull = false;
		while(!isFull) {
			
			if(counter==0)
			{
				p = processes.get(counter);
				while(p.getRunTime() > 0) {
					result.add(p);
					p.setRunTime(p.getRunTime() - 1);
					runTime++;
				}
				counter++;
			}
			else
			{
				if(counter < Total_Processes){
					p = processes.get(counter);
				}
				
				if(holdOn < Total_Processes){
					p2 = processes.get(holdOn);
				}
				
				if(p.getRunTime() < p2.getRunTime())
				{
					while(p.getRunTime() > 0) {
						result.add(p);
						p.setRunTime(p.getRunTime() - 1);
						runTime++;
					}
					if(counter < holdOn) {
						counter = holdOn;
					} else {
						counter++;
					}
					holdOn++;
				}
				else
				{
					while(p2.getRunTime() > 0) {
						result.add(p2);
						p2.setRunTime(p2.getRunTime() - 1);
						runTime++;
					}
					holdOn++;
				}	
			}
			
			if(processes.get(Total_Processes-1).getRunTime()<0 && p.getRunTime()>0)
			{
				while(p.getRunTime() > 0) {
					result.add(p);
					p.setRunTime(p.getRunTime() - 1);
					runTime++;
				}
			}
						
			System.out.println("Total runTime= "+runTime);
			
			if(runTime >= 100) {
				isFull = true;
			}
		}
		
		
		return result;
	}
}
