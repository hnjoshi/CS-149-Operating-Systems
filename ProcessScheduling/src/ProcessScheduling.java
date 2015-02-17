import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class ProcessScheduling {
	public static void main(String[] args) {
		
		double avgTurnAroundTimeFCFS = 0.0;
		double avgWaitTimeFCFS = 0.0;
		double avgResponseTimeFCFS = 0.0;
		double avgThroughputFCFS = 0.0;
		
		double avgTurnAroundTimeSJF = 0.0;
		double avgWaitTimeSJF = 0.0;
		double avgResponseTimeSJF = 0.0;
		double avgThroughputSJF = 0.0;
		
		double avgTurnAroundTimeSRT = 0.0;
		double avgWaitTimeSRT = 0.0;
		double avgResponseTimeSRT = 0.0;
		double avgThroughputSRT = 0.0;
		
		double avgTurnAroundTimeRR = 0.0;
		double avgWaitTimeRR = 0.0;
		double avgResponseTimeRR = 0.0;
		double avgThroughputRR = 0.0;
		
		double avgTurnAroundTimeHPFP = 0.0;
		double avgWaitTimeHPFP = 0.0;
		double avgResponseTimeHPFP = 0.0;
		double avgThroughputHPFP = 0.0;
		
		double avgTurnAroundTimeHPFNP = 0.0;
		double avgWaitTimeHPFNP = 0.0;
		double avgResponseTimeHPFNP = 0.0;
		double avgThroughputHPFNP = 0.0;
		
		for(int z = 0; z < 5; z++) {
			System.out.println("\n==================================Running the Simulation #"+(z+1)+"=============================");
			int qCounter = 0;
			ArrayList<Process> processes = new ArrayList<Process>();
			
			while(qCounter < 100) { 
				Random rn = new Random();
				float aTime = rn.nextFloat() * (99 - 0) + 0;
	
				float rTime = (float) (rn.nextFloat() * (10 - 0.1) + 0.1);
				
				//random.nextInt(max - min + 1) + min
				int priority = rn.nextInt(4 - 1 + 1) + 1;
				
				Process p = new Process(aTime, rTime, priority);
				processes.add(p);
				
				qCounter += rTime;
			}
			
			Collections.sort(processes, new Comparator<Process>() {
			    public int compare(Process p1, Process p2) {
			    	float diff = p1.getArrivalTime() - p2.getArrivalTime();
			    	if(diff < 0) {
			    		return -1;
			    	} else if (diff == 0) {
			    		return 0;
			    	} else {
			    		return 1;
			    	}
			    }
			});
			
			int alpha = 65;
			for(int i = 0; i < processes.size(); i++) {
				processes.get(i).setName((char) alpha);
				alpha++;
				if(alpha > 90 && alpha<97) {alpha = 97;}
			}
			
			System.out.println("\tInitial Simulation Processes: \n");
			System.out.print("pID\t|\tAT\t|\tRT\t|   Priority\n");
			for(Process p : processes) {
				System.out.println(p);
			}
			System.out.println("---------------------------------------------------------------");
			
			
			//Start of the First come first served.
			System.out.println("Executing First Come First Served");
			
			ArrayList<Process> processesForFCFS = new ArrayList<Process>();
			//Make a Deep Copy of the Original Processes
			for(Process p : processes) {
				processesForFCFS.add(p.clone());
			}
			
			FirstJobFirst f = new FirstJobFirst(processesForFCFS);
			ArrayList<Process> firstComeFirst = f.fcfs();
			
			for(int i = 0; i < firstComeFirst.size(); i++) {
				System.out.print(firstComeFirst.get(i).getName() + " ");
			}
			System.out.println();
			System.out.printf("Average Turn Around Time for FCFS: %.1f\n", f.getFJF_avgTurnaroundTime());
			System.out.printf("Average Wait Time for FCFS: %.1f\n", f.getFJF_avgWaitTime());
			System.out.printf("Average Response Time for FCFS: %.1f\n", f.getFJF_avgResponseTime());
			System.out.printf("Average Throughput for FCFS: %.1f\n", f.get_throughput());
			avgTurnAroundTimeFCFS +=  f.getFJF_avgTurnaroundTime();
			avgWaitTimeFCFS += f.getFJF_avgWaitTime();
			avgResponseTimeFCFS += f.getFJF_avgResponseTime();
			avgThroughputFCFS += f.get_throughput();
			
			//END of the First come first served. 
			
			System.out.println("---------------------------------------------------------------");
			
			//Start of the Shortest Job First. 
			System.out.println("Executing Shortest Job First.");
			
			ArrayList<Process> processesForSJF = new ArrayList<Process>();
			//Make a Deep Copy of the Original Processes
			for(Process p : processes) {
				processesForSJF.add(p.clone());
			}
			
			ShortestJobFirst sjf = new ShortestJobFirst(processesForSJF);
			ArrayList<Process> shortestJobFirst = sjf.sjf();
			
			for(Process p : shortestJobFirst) {
				System.out.print(p.getName() + " ");
			}
			System.out.println();
			// avgTurnAroundTimeSJF += getter for turnaround time for SJF
			// avgWaitTimeSJF += 
			// avgResponseTimeSJF +=
			// avgThroughputSJF +=
			
			//END of the Shortest Job First. 
			System.out.println("---------------------------------------------------------------");
			
			// Start of Shortest Remaining Time
					System.out.println("Executing Shortest Remaining Time");
					ArrayList<Process> processesForSRT = new ArrayList<Process>();
					//Make a Deep Copy of the Original Processes
					for(Process p : processes) {
						processesForSRT.add(p.clone());
					}
					
					
					
					//Call Shortest Remaining Time Algorithm. 
							ShortestRemainingTime srt = new ShortestRemainingTime(processesForSRT);
							//ArrayList<Process> shortestRemaining = srt.srt();
							ArrayList<Process> shortestRemaining = srt.srtxyz();
							
							for(Process p : shortestRemaining) {
								System.out.print(p.getName() + " ");
							}
							System.out.println();
							System.out.printf("Average Turn Around Time for SRT: %.1f\n", srt.averageTurnAroundTime(shortestRemaining));
							System.out.printf("Average Wait Time for SRT: %.1f\n", srt.averageWaitTime(shortestRemaining));
							System.out.printf("Average Response Time for SRT: %.1f\n", srt.averageResponseTime(shortestRemaining));
							System.out.printf("Average Throughput for SRT: %.5f\n", srt.getThroughPut(shortestRemaining));

							avgTurnAroundTimeSRT += srt.averageTurnAroundTime(shortestRemaining);
							avgWaitTimeSRT += srt.averageWaitTime(shortestRemaining);
							avgResponseTimeSRT += srt.averageResponseTime(shortestRemaining);
							avgThroughputSRT += srt.getThroughPut(shortestRemaining);
	
			System.out.println("---------------------------------------------------------------");
	
			//END OF SHORTEST REMAINING TIME
			
			//Start of the Round Robin Calls. 
			System.out.println("Executing Round Robin");
			ArrayList<Process> processesForRR = new ArrayList<Process>();
			//Make a Deep Copy of the Original Processes
			for(Process p : processes) {
				processesForRR.add(p.clone());
			}
			
			//Call Round Robin Algorithm. 
			RoundRobin RR = new RoundRobin(processesForRR);
			ArrayList<Process> roundRobin = RR.roundRobin();
			
			for(Process p : roundRobin) {
				System.out.print(p.getName() + " ");
			}
			System.out.println();
			System.out.printf("Average Turn Around Time for RR: %.1f\n", RR.averageTurnAroundTime(roundRobin));
			System.out.printf("Average Wait Time for RR: %.1f\n", RR.averageWaitTime(roundRobin));
			System.out.printf("Average Response Time for RR: %.1f\n", RR.averageResponseTime(roundRobin));
			System.out.printf("Average Throughput for RR: %.5f\n", RR.getThroughPut(roundRobin));
			
			avgTurnAroundTimeRR += RR.averageTurnAroundTime(roundRobin);
			avgWaitTimeRR += RR.averageWaitTime(roundRobin);
			avgResponseTimeRR += RR.averageResponseTime(roundRobin);
			avgThroughputRR += RR.getThroughPut(roundRobin);
			
			//END of the Round Robins. 
			
			System.out.println("---------------------------------------------------------------");
	  		//Start of Highest Priority First. 
			System.out.println("Executing Highest Priority First [Non Preemptive]");
			ArrayList<Process> processesForHPFNP = new ArrayList<Process>();
			//Make a Deep Copy of the Original Processes
			for(Process p : processes) {
				processesForHPFNP.add(p.clone());
			}
			
			HighestPiorityFirst h = new HighestPiorityFirst(processesForHPFNP);
			ArrayList<Process> highestPriorityFirst = h.hpfnp();
			
			for(int i = 0; i < highestPriorityFirst.size(); i++) {
				System.out.print(highestPriorityFirst.get(i).getName() + " ");
			}
			System.out.println();
			
			//avgTurnAroundTimeHPFNP += getters for turnaround time of HPF non preemptive. 
			//avgWaitTimeHPFNP += 
			//avgResponseTimeHPFNP += 
			//avgThroughputHPFNP +=
			
			System.out.println("---------------------------------------------------------------");
			System.out.println("Executing Highest Priority First [Preemptive]");

			ArrayList<Process> processesForHPFP = new ArrayList<Process>();
			//Make a Deep Copy of the Original Processes
			for(Process p : processes) {
				processesForHPFP.add(p.clone());
			}
			
			HighestPiorityFirst hp = new HighestPiorityFirst(processesForHPFP);
			ArrayList<Process> highestPriorityFirstp = hp.hpfp();
			
			for(int i = 0; i < highestPriorityFirstp.size(); i++) {
				System.out.print(highestPriorityFirstp.get(i).getName() + " ");
			}
			System.out.println();
			hp.getTurnaroundTime(highestPriorityFirstp);

			//avgTurnAroundTimeHPFP +=
			//avgWaitTimeHPFP +=
			//avgResponseTimeHPFP +=
			//avgThroughputHPFP +=
			
			//End of Highest Priority First. 
			System.out.println("---------------------------------------------------------------");
		}
		
		avgTurnAroundTimeFCFS /= 5.0;
		avgWaitTimeFCFS /= 5.0;
		avgResponseTimeFCFS /= 5.0;
		avgThroughputFCFS /= 5.0;
		
		avgTurnAroundTimeSJF /= 5.0;
		avgWaitTimeSJF /= 5.0;
		avgResponseTimeSJF /= 5.0;
		avgThroughputSJF /= 5.0;
		
		avgTurnAroundTimeSRT /= 5.0;
		avgWaitTimeSRT /= 5.0;
		avgResponseTimeSRT /= 5.0;
		avgThroughputSRT /= 5.0;
		
		avgTurnAroundTimeRR /= 5.0;
		avgWaitTimeRR /= 5.0;
		avgResponseTimeRR /= 5.0;
		avgThroughputRR /= 5.0;
		
		avgTurnAroundTimeHPFP /= 5.0;
		avgWaitTimeHPFP /= 5.0;
		avgResponseTimeHPFP /= 5.0;
		avgThroughputHPFP /= 5.0;
		
		avgTurnAroundTimeHPFNP /= 5.0;
		avgWaitTimeHPFNP /= 5.0;
		avgResponseTimeHPFNP /= 5.0;
		avgThroughputHPFNP /= 5.0;
		
		System.out.println("=======================Final Averages for Each Algorithms==========================");
		System.out.println("Averages for FCFS");
		System.out.printf("Average Turn Around Time: %.2f\n", avgTurnAroundTimeFCFS);
		System.out.printf("Average Wait Time: %.2f\n", avgWaitTimeFCFS);
		System.out.printf("Average Response Time: %.2f\n", avgResponseTimeFCFS);
		System.out.printf("Average Throughput: %.2f\n", avgThroughputFCFS);
		
		System.out.println("Averages for SJF");
		System.out.printf("Average Turn Around Time: %.2f\n", avgTurnAroundTimeSJF);
		System.out.printf("Average Wait Time: %.2f\n", avgWaitTimeSJF);
		System.out.printf("Average Response Time: %.2f\n", avgResponseTimeSJF);
		System.out.printf("Average Throughput: %.2f\n", avgThroughputSJF);
		
		System.out.println("Averages for SRT");
		System.out.printf("Average Turn Around Time: %.2f\n", avgTurnAroundTimeSRT);
		System.out.printf("Average Wait Time: %.2f\n", avgWaitTimeSRT);
		System.out.printf("Average Response Time: %.2f\n", avgResponseTimeSRT);
		System.out.printf("Average Throughput: %.2f\n", avgThroughputSRT);
		
		System.out.println("Averages for RR");
		System.out.printf("Average Turn Around Time: %.2f\n", avgTurnAroundTimeRR);
		System.out.printf("Average Wait Time: %.2f\n", avgWaitTimeRR);
		System.out.printf("Average Response Time: %.2f\n", avgResponseTimeRR);
		System.out.printf("Average Throughput: %.2f\n", avgThroughputRR);
		
<<<<<<< HEAD
		for(int i = 0; i < highestPriorityFirstp.size(); i++) {
			System.out.print(highestPriorityFirstp.get(i).getName() + " ");
		}
		System.out.println();
      System.out.println(processes.size());
      System.out.println(hp.getTurnaroundTime(highestPriorityFirstp));
		System.out.println(hp.getWaitingTime(highestPriorityFirstp));
		//End of Highest Priority First. 
		System.out.println("---------------------------------------------------------------");
=======
		System.out.println("Averages for HPF [Non preemptive]");
		System.out.printf("Average Turn Around Time: %.2f\n", avgTurnAroundTimeHPFNP);
		System.out.printf("Average Wait Time: %.2f\n", avgWaitTimeHPFNP);
		System.out.printf("Average Response Time: %.2f\n", avgResponseTimeHPFNP);
		System.out.printf("Average Throughput: %.2f\n", avgThroughputHPFNP);
		
		System.out.println("Averages for HPF [preemptive]");
		System.out.printf("Average Turn Around Time: %.2f\n", avgTurnAroundTimeHPFP);
		System.out.printf("Average Wait Time: %.2f\n", avgWaitTimeHPFP);
		System.out.printf("Average Response Time: %.2f\n", avgResponseTimeHPFP);
		System.out.printf("Average Throughput: %.2f\n", avgThroughputHPFP);
>>>>>>> origin/master
	}
}
