import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Swapping {
	public static void main(String[] args) {
		
		int firstFitSwappedIn = 0;
		int nextFitSwappedIn = 0;
		int bestFitSwappedIn = 0;
		int worstFitSwappedIn = 0;
		
		int firstFitSwappedOut = 0;
		int nextFitSwappedOut = 0;
		int bestFitSwappedOut = 0;
		int worstFitSwappedOut = 0;
		
		for(int i = 0; i < 5; i++) {
		LinkedList<Process> process = generateProcess();
		
		for(Process p : process) {
			System.out.println(p);
		}
		
		LinkedList<Process> firstFitProcesses = new LinkedList<Process>();
		for(Process p : process) {
			firstFitProcesses.add(p.clone());
		}
		
		LinkedList<Process> nextFitProcesses = new LinkedList<Process>();
		for(Process p : process) {
			nextFitProcesses.add(p.clone());
		}
		
		LinkedList<Process> bestFitProcesses = new LinkedList<Process>();
		for(Process p : process) {
			bestFitProcesses.add(p.clone());
		}
		
		LinkedList<Process> worstFitProcesses = new LinkedList<Process>();
		for(Process p : process) {
			worstFitProcesses.add(p.clone());
		}

		System.out.println("\n############################################################################\n");
		System.out.println("\nFIRST FIT ALGORITHM SIMULATION\n");
		FirstFit firstFit = new FirstFit(firstFitProcesses);
		firstFit.run();
		
		System.out.println("\n############################################################################\n");
		System.out.println("\nNEXT FIT ALGORITHM SIMULATION\n");
		NextFit nextFit = new NextFit(nextFitProcesses);
		nextFit.run();
		
		System.out.println("\n############################################################################\n");
		System.out.println("\nBEST FIT ALGORITHM SIMULATION\n");
		BestFit bestFit = new BestFit(bestFitProcesses);
		bestFit.run();
		
		System.out.println("\n############################################################################\n");
		System.out.println("\nWORST FIT ALGORITHM SIMULATION\n");
		WorstFit worstFit = new WorstFit(worstFitProcesses);
		worstFit.run();
		
		firstFitSwappedIn += firstFit.getSwappedIn();
		firstFitSwappedOut += firstFit.getSwappedOut();
		nextFitSwappedIn += nextFit.getSwappedIn();
		nextFitSwappedOut += nextFit.getSwappedOut();
		bestFitSwappedIn += bestFit.getSwappedIn();
		bestFitSwappedOut += bestFit.getSwappedOut();
		worstFitSwappedIn += worstFit.getSwappedIn();
		worstFitSwappedOut += worstFit.getSwappedOut();
		}
		
		System.out.println("\n############################################################################\n");
		System.out.println("\nSTATISTICS FOR EACH ALGORITHMS\n");
		
		System.out.println("FIRST FIT ALGORITHM");
		System.out.println("Average Processes Swapped In: " + (firstFitSwappedIn / 5.0));
		System.out.println("Average Processes Swapped Out: " + (firstFitSwappedOut / 5.0));
		
		System.out.println("NEXT FIT ALGORITHM");
		System.out.println("Average Processes Swapped In: " + (nextFitSwappedIn / 5.0));
		System.out.println("Average Processes Swapped Out: " + (nextFitSwappedOut / 5.0));
		
		System.out.println("BEST FIT ALGORITHM");
		System.out.println("Average Processes Swapped In: " + (bestFitSwappedIn / 5.0));
		System.out.println("Average Processes Swapped Out: " + (bestFitSwappedOut / 5.0));
		
		System.out.println("WORST FIT ALGORITHM");
		System.out.println("Average Processes Swapped In: " + (worstFitSwappedIn / 5.0));
		System.out.println("Average Processes Swapped Out: " + (worstFitSwappedOut / 5.0));
		
	}
	
	public static LinkedList<Process> generateProcess() {
		LinkedList<Process> readyQueue = new LinkedList<Process>();
		int nameCounter = 0;
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-+={}[]|:'/~`?<>,;€ƒ†‡ŠŒŽŸ•™‰ÀÈÌÒÙáéíóúýãñõÃÑÕäëïöüÿÄËÏÖÜŸ";
		char[] names = str.toCharArray(); 
		int[] sizes = {5, 11, 17, 31};

		do{
			Process p = new Process();
			int sizeIndex = (int) (Math.random() * 4);
			int duration = (int) ((Math.random() * 5)+1);
                        
			p.setSize(sizes[sizeIndex]);
			p.setDuration(duration);
			p.setName(names[nameCounter++]);
			readyQueue.add(p);
		} while(nameCounter < names.length); 
		return readyQueue;
	}
}
