import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Swapping {
	public static void main(String[] args) {
		LinkedList<Process> process = generateProcess();
		
		for(Process p : process) {
			System.out.println(p);
		}
		
		WorstFit worstFit = new WorstFit(process);
		worstFit.run();
	}
	
	public static LinkedList<Process> generateProcess() {
		LinkedList<Process> readyQueue = new LinkedList<Process>();
		int durationCounter = 0;
		int nameCounter = 65;
		int[] sizes = {5, 11, 17, 31};
		do{
			Process p = new Process();
			int sizeIndex = (int) (Math.random() * 4);
			int duration = (int) ((Math.random() * 5)+1);
                        
			p.setSize(sizes[sizeIndex]);
			p.setDuration(duration);
			p.setName((char) nameCounter++);
			
			//If we run out of A-Z start with a-z. 
			if(nameCounter == 91) {
				nameCounter = 97;
			}
			
			readyQueue.add(p);
			durationCounter += duration;
		} while(durationCounter < 60);
		
		return readyQueue;
	}
}
