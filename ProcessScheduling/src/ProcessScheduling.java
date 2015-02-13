import java.util.ArrayList;
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
		
		FirstJobFirst f = new FirstJobFirst(processes);
		ArrayList<Process> firstComeFirst = f.fcfs();
		
		for(int i = 0; i < firstComeFirst.size(); i++) {
			System.out.print(firstComeFirst.get(i).getName() + " ");
		}
	}
}
