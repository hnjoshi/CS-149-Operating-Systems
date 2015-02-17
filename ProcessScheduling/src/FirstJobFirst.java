import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class runs First job First Algorithm.
 * @author Kernel
 */
public class FirstJobFirst {
	ArrayList<Process> processes;
	int waitTime[];
	int turnAroundTime[];
	float avgWaitTime, avgResponseTime, avgTurnaroundTime, throughput;
	int noOfProcessesExec;
	
	public FirstJobFirst(ArrayList<Process> p) {
		processes = p;
	}
	
	public ArrayList<Process> fcfs() {
		ArrayList<Process> result = new ArrayList<Process>();
		boolean isFull = false;
		int counter = 0, runTime = 0;
		waitTime = new int[processes.size()];
		turnAroundTime = new int[processes.size()];
		Process p = null;
				
		
		while(!isFull) {
			
			p = processes.get(counter);
			
			waitTime[counter] = runTime;
			
			while(p.getRunTime() > 0) {
				result.add(p);
				p.setRunTime(p.getRunTime() - 1);
				runTime++;
			}
			counter++;
			
			if(runTime >= 100) {
				isFull = true;
				noOfProcessesExec += counter;
			}
		}
		//System.out.println("Total processes arrived = "+Total_Processes);
		//System.out.println("Total processes served = "+(counter));
		//System.out.println("Total processes runtime = "+runTime);
		//System.out.println("\nTotal runtime = "+runTime);
		//System.out.println("Last Process served was "+ p.getName()+"."
		//		+ " at " + counter +"\n");
		
		//System.out.println(Arrays.toString(waitTime));
		int totalWaits = 0;
		for(int i=0; i<waitTime.length; i++)
		{
			totalWaits += waitTime[i];
		}
		
		for(int i=0; i<turnAroundTime.length-1; i++)
		{
			turnAroundTime[i] = waitTime[i+1] - waitTime[i];
		}
		turnAroundTime[counter-1] = runTime - waitTime[counter-1];
		//System.out.println(Arrays.toString(turnAroundTime));
		int totalTurnAroundTime = 0;
		for(int i=0; i<turnAroundTime.length; i++)
		{
			totalTurnAroundTime += turnAroundTime[i];
		}
		
		//System.out.println("Total wait time = "+totalWaits);
		avgWaitTime = (float)totalWaits/noOfProcessesExec;
		System.out.println("avg wait time = "+avgWaitTime);
		avgResponseTime = avgWaitTime;
		System.out.println("avg response time = "+avgResponseTime);
		avgTurnaroundTime = (float)totalTurnAroundTime/noOfProcessesExec;
		System.out.println("avg turnaround time = "+avgTurnaroundTime);
		throughput = (float)noOfProcessesExec/runTime;
		System.out.println("Throughput "+ noOfProcessesExec+"/" + runTime +" = "+throughput+"\n");
		
		return result;
	}
	
	public float getFJF_avgWaitTime()
	{
		return avgWaitTime;
	}
	
	public float getFJF_avgResponseTime()
	{
		return avgResponseTime;
	}
	
	public float getFJF_avgTurnaroundTime()
	{
		return avgTurnaroundTime;
	}
	
	public float get_throughput()
	{
		return throughput;
	}
}
