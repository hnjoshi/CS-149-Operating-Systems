import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class runs Shortest job First Algorithm.
 * @author Kernel
 */
public class ShortestJobFirst {
	
	Process p = null;
	int current = 0, runTime = 0;
	
	ArrayList<Process> processes; 
	public ShortestJobFirst(ArrayList<Process> p) {
		processes = p;
	}
	
	//This function performs the Shortest Job First Algorithm. 
	public ArrayList<Process> sjf() {
		ArrayList<Process> result = new ArrayList<Process>();
		ArrayList<Process> readyQ = new ArrayList<Process>();
		
		boolean isFull = false;
		while(!isFull) {
			
			if(current==0)
			{
				p = processes.get(current);
				while(p.getRunTime() > 0) {
					result.add(p);
					p.setRunTime(p.getRunTime() - 1);
					runTime++;
				}
				processes.remove(current);
				current++;
			}
			else
			{
				if(processes.size()>0){
					// add all the processes to readyQ with RT <= total runtime
					int i=0;
					while(i<processes.size() && processes.get(i).getArrivalTime() <= runTime)
					{
						readyQ.add(processes.get(i));
						i++;
					}
				}
				
				// find the smallest RT process from readyQ				
				if(readyQ.size()>0){
					
					int nextPid = 0;
					float smallRT = readyQ.get(0).getRunTime();
					for(int j=1; j<readyQ.size(); j++)
					{
						if(readyQ.get(j).getRunTime() < smallRT){
							smallRT = readyQ.get(j).getRunTime();
							nextPid = j;
						}
					}
				
					//run the readyQ process with nextPid
					p = readyQ.get(nextPid);
					while(p.getRunTime() > 0) {
						result.add(p);
						p.setRunTime(p.getRunTime() - 1);
						runTime++;
					}
					
					// Remove same process original processes
					for(int j=0; j<processes.size(); j++)
					{	
						if(p.getName() == processes.get(j).getName())
						{
							processes.remove(j);
						}
					}
					
					// finally clear the readyQ
					readyQ.clear();
				}
				else
				{
					runTime++; // it will be nice if we can add '-'
				}
			}
			
			if(runTime >= 100) {
				isFull = true;
			}
		}
		
		return result;
	}
}
