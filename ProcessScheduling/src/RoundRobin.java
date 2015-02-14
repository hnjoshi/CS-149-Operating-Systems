import java.util.ArrayList;


public class RoundRobin {
	
	private ArrayList<Process> processes;
	public RoundRobin(ArrayList<Process> p) {
		processes = p;
	}
	
	public ArrayList<Process> roundRobin() {
		ArrayList<Process> result = new ArrayList<Process>();
		int counter = 0, size = processes.size(), index = 0;
		boolean isEmpty = true;
		
		while (isEmpty) {
			index = index % size;
			float runTime = processes.get(index).getRunTime();
			if(runTime > 0) {
				result.add(processes.get(index));
				processes.get(index).setRunTime(runTime - 1);
				
				if(processes.get(index).getRunTime() < 0) {
					counter++;
				}
				
				if(counter == size) {
					isEmpty = false;
				}
			}
			index++;
		}
		
		System.out.println("Total Size is: " + result.size());
		return result;
	}
}
