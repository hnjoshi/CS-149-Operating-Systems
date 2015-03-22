import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This algorithm will implement WorstFit algorithm for memory management. 
 * @author Jay
 */
public class WorstFit {
	private static int MINUTES, SMALLEST_SIZE, MAIN_MEMORY_SIZE;
	private LinkedList<Process> process;
	private ArrayList<Process> currentProcess;
	private char[] mainMemory;
	private char[] mariMemory;
	private int freeSpace, swappedIn, swappedOut, memCopy; 
	
	public int getSwappedIn() {
		return swappedIn;
	}

	public int getSwappedOut() {
		return swappedOut;
	}
	
	public int getMemCopy() {
		return memCopy;
	}

	public WorstFit(LinkedList<Process> p) {
		MINUTES = 60; SMALLEST_SIZE = 5; MAIN_MEMORY_SIZE = 100;
		process = p;
		mainMemory = new char[MAIN_MEMORY_SIZE];
		mariMemory = new char[MAIN_MEMORY_SIZE];
		currentProcess = new ArrayList<Process>();	
		freeSpace = 100;
	    swappedIn = 0;
	    swappedOut = 0;
		
		for(int i = 0; i < MAIN_MEMORY_SIZE; i++) {
			mainMemory[i] = '.';
			mariMemory[i] = '.';
		}
	}
	
	public void run() {
		initialFill();
		for(int i = 1; i < MINUTES; i++) {			
			int j = 0, tempDuration = 0; 
			while(j < currentProcess.size()) {
				tempDuration = currentProcess.get(j).getDuration()-1;
				currentProcess.get(j).setDuration(tempDuration);
				if(tempDuration == 0) {
					Process p = currentProcess.remove(j);
					swappedOut++;
					freeSpace += p.getSize();
					
					String t = String.format("time  %2d: Swapped Out Process %s", i, p.getName());
					System.out.println(t);
					swapOut(p);
					print();
				} else {
					j++;
				}
			}
			
			if(i==30){
				compaction();
			}
			
			j = 0;
			while(freeSpace >= SMALLEST_SIZE && j < process.size()) {
				int[] biggestBlock = biggestEmptyBlock();
				int count = biggestBlock[1];
				if(process.get(j).getSize() <= biggestBlock[0]) {
					Process p = process.remove(j);
					int size = p.getSize();
					char name = p.getName();
					for(int k = 0; k < size; k++) {
						mainMemory[count] = name;
						count++;
					}
					currentProcess.add(p);
					freeSpace -= size;
					swappedIn++;
					String t = String.format("time %2d: Swapped In Process %s Size is %2d Duration is %d", i, p.getName(), p.getSize(), p.getDuration());
					System.out.println(t);
					print();
				} else {
					j++;
				}
			}
		} 
	}
	
	private void initialFill() {
		int count = 0, i = 0;
		while(freeSpace >= SMALLEST_SIZE && i < process.size()) {
			if(process.get(i).getSize() <= freeSpace) {
				Process p = process.remove(i);
				int size = p.getSize();
				char name = p.getName();
				for(int j = 0; j < size; j++) {
					mainMemory[count] = name;
					count++;
				}
				currentProcess.add(p);
				freeSpace -= size;
				swappedIn++;
				String t = String.format("time  0: Swapped In Process %s Size is %2d Duration is %d", p.getName(), p.getSize(), p.getDuration());
				System.out.println(t);
				print();
			} else {
				i++;
			}
		}
	}
	
	private void swapOut(Process p) {
		char name = p.getName();
		int size = p.getSize(), i = 0;
		
		for(;i < MAIN_MEMORY_SIZE; i++) {
			if(mainMemory[i] == name) {
				break;
			}
		}
		size += i;
		for(;i < size && i < MAIN_MEMORY_SIZE; i++) {
			mainMemory[i] = '.';
		}
	}
	
	/**
	 * Returns the largest Empty block size, and the index of the biggest Empty Block
	 * @return index of the biggest Empty block.
	 */
	private int[] biggestEmptyBlock() {
		int index = -1, max = 0, currentMax = 0, tempIndex = -1;
		int[] result = new int[2];
		boolean isFirst = true;
		
		for(int i = 0; i < MAIN_MEMORY_SIZE; i++) {
			if(mainMemory[i] == '.') {
				currentMax++;
				if(isFirst) {
					isFirst = false;
					tempIndex = i;
				}
			} else {
				if(max < currentMax) {
					max = currentMax;
					index = tempIndex;
					result[0] = max;
					result[1] = index;
				} 
				currentMax = 0;
				isFirst = true;
			}
		}
		
		if(max < currentMax) {
			max = currentMax;
			index = tempIndex;
			result[0] = max;
			result[1] = index;
		} 
		currentMax = 0;
		isFirst = true;
		
		return result;
	}
	
	public void print() {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < MAIN_MEMORY_SIZE; i++) {
			buffer.append(mainMemory[i]);
		}
		System.out.println(buffer.toString());
	}
	
	public void compaction(){
		
		System.out.println("Compaction is about to Start");
		
		// actual compaction
		int ctr = 0;
		int myCtr = 0, isFull = 0;
		boolean isFirst = false;
		
		for(int i=0; i<MAIN_MEMORY_SIZE; i++){
			if(mainMemory[i] != '.'){
				mariMemory[ctr++] = mainMemory[i];
				isFull++;
			} else {
				if(!isFirst) {
					isFirst = true;
					myCtr = i;
				}
			}
		}
		
		if(isFull != MAIN_MEMORY_SIZE) {
			for(int i = myCtr; i < MAIN_MEMORY_SIZE; i++) {
				if(mainMemory[i] != '.') {
					memCopy++;
				}
			}
		}
		
		mainMemory = mariMemory;
		mariMemory = null;
		
		System.out.println("Compaction has completed");
		System.out.println("Compacted memory print start");
		print();
		System.out.println("Compacted memory print end");
		
	}
}
