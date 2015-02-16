import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class runs Shortest job First Algorithm.
 * @author Kernel
 */
public class ShortestJobFirst {
	
	Process p = null;
	int waitTime[], responseTime[];
	int turnAroundTime[];
	float avgWaitTime, avgResponseTime, avgTurnaroundTime, throughput;
	int noOfProcessesExec;
	int current = 0, runTime = 0;
	
	ArrayList<Process> processes; 
	public ShortestJobFirst(ArrayList<Process> p) {
		processes = p;
	}
	
	//This function performs the Shortest Job First Algorithm. 
	public ArrayList<Process> sjf() {
		ArrayList<Process> result = new ArrayList<Process>();
		ArrayList<Process> readyQ = new ArrayList<Process>();
		waitTime = new int[processes.size()];
		responseTime = new int[processes.size()];
		
		boolean isFull = false;
		while(!isFull) {
			
			if(current==0)
			{
				p = processes.get(current);
				responseTime[current] = runTime;
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

					responseTime[current] = runTime;
					
					//run the readyQ process with nextPid
					p = readyQ.get(nextPid);
					while(p.getRunTime() > 0) {
						result.add(p);
						p.setRunTime(p.getRunTime() - 1);
						runTime++;
					}
					current++;	//counting number of processes executed
					
					// Remove same process from original processes
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
		
		//System.out.println("Total processes = "+ current);
		//System.out.println(Arrays.toString(responseTime));
		int totalResponse = 0;
		for(int i=0; i<responseTime.length; i++){
			totalResponse += responseTime[i];
		}
		avgResponseTime = totalResponse/current;
		System.out.println("avg response time = "+avgResponseTime);
		throughput = (float)current/runTime;
		System.out.println("Throughput = "+ throughput);
		
		return result;
	}
	
	public float getSJF_avgWaitTime()
	{
		return avgWaitTime;
	}
	
	public float getSJF_avgResponseTime()
	{
		return avgResponseTime;
	}
	
	public float getSJF_avgTurnaroundTime()
	{
		return avgTurnaroundTime;
	}
	
	public float getSJF_throughput()
	{
		return throughput;
	}
}
