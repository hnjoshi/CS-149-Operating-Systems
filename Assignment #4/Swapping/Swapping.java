import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Swapping {
	public static void main(String[] args) {
		Queue<Process> process = generateProcess();
		
		for(Process p : process) {
			System.out.println(p);
		}
	}
	
	public static Queue<Process> generateProcess() {
		Queue<Process> readyQueue = new LinkedList<Process>();
		int durationCounter = 0;
		int nameCounter = 65;
		int[] sizes = {5, 11, 17, 31};
		do{
			Process p = new Process();
			Random rn = new Random();
			int sizeIndex = rn.nextInt() % 4;
			int duration = (rn.nextInt() % 5) + 1;		
			
			p.setSize(sizes[sizeIndex]);
			p.setDuration(duration);
			p.setName((char) nameCounter++);
			
			//If we run out of A-Z start with a-z. 
			if(nameCounter > 90 && nameCounter < 97) {
				nameCounter = 97;
			}
			
			readyQueue.add(p);
			durationCounter += duration;
		} while(durationCounter < 60);
		
		return readyQueue;
	}
}
