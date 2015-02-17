import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;


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
<<<<<<< HEAD
		System.out.println("Total Size is: " + result.size());
=======
>>>>>>> origin/master
		return result;
	}
	
	public double averageTurnAroundTime(ArrayList<Process> result) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for(int i = 0; i < result.size(); i++) {
			map.put(result.get(i).getName(), i);
		}
		
		int distinct = map.size();
		double sum = 0;
		for (Entry<Character, Integer> entry : map.entrySet())
		{
			sum += entry.getValue() + 1;
		}
		double average = sum / distinct;
		return average;
	}
	
	public double averageWaitTime(ArrayList<Process> result) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i = 0; i < result.size(); i++) {
			if(map.get(result.get(i).getName()) != null) {
				map.put(result.get(i).getName(), map.get(result.get(i).getName()) + 1);
			} else {
				map.put(result.get(i).getName(), 1);
			}
		}
		
		double sum = 0;
		
		for (Entry<Character, Integer> entry : map.entrySet())
		{
			int count = entry.getValue();
			int lastIndex = lastIndex(result, entry.getKey()) + 1;
			sum += lastIndex - count;
		}
		int distinct = map.size();
		double average = sum / distinct;
		return average;
	}
	
	private int lastIndex(ArrayList<Process> result, char c) {
		for(int i = result.size() - 1; i >= 0; i--) {
			if(result.get(i).getName() == c) {
				return i;
			}
		}
		return 0;
	}
	
	public double averageResponseTime(ArrayList<Process> result) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i = 0; i < result.size(); i++) {
			if(map.get(result.get(i).getName()) != null) {
			} else {
				map.put(result.get(i).getName(), i);
			}
		}
		
		double sum = 0;
		
		for (Entry<Character, Integer> entry : map.entrySet())
		{
			sum += entry.getValue();
		}
		int distinct = map.size();
		double average = sum / distinct;
		return average;
	} 
	
	public double getThroughPut(ArrayList<Process> result) {
		double avg = 0;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i = 0; i < result.size(); i++) {
			if(map.get(result.get(i).getName()) != null) {
			} else {
				map.put(result.get(i).getName(), i);
			}
		}
		avg = (map.size() + 0.0) / 100;
		return avg;
	}
}
