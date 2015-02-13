import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class ProcessScheduling {
	public static void main(String[] args) {
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
		    	return  (int) (p1.getArrivalTime() - p2.getArrivalTime());
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
		System.out.println("---------------------------------------------------------------\n\n");
		
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
		
		System.out.println("---------------------------------------------------------------\n\n");
		
		ArrayList<Process> processesForSJF = new ArrayList<Process>();
		//Make a Deep Copy of the Original Processes
		for(Process p : processes) {
			processesForSJF.add(p.clone());
		}
		
		ShortestJobFirst sjf = new ShortestJobFirst(processesForSJF);
		
		ArrayList<Process> shortestJobFirst = sjf.sjf();
		
		System.out.println("---------------------------------------------------------------\n\n");
      		
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
		
		System.out.println("---------------------------------------------------------------\n\n");
	
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
		
		System.out.println("---------------------------------------------------------------\n\n");
	}
}
