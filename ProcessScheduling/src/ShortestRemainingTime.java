import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;


public class ShortestRemainingTime {

	private ArrayList <Process> processes;
	
	public ShortestRemainingTime(ArrayList <Process> p)
	{
		processes = p;
	}
	
	
	//Jagrut
	
	public ArrayList<Process> srtxyz() {
		
		ArrayList<Process> result = new ArrayList<Process>();
		ArrayList<Process> remainingJob = new ArrayList<Process>();
		int runtime = 0, processCounter = 0;
		boolean firstTime = true;
	
		while(runtime <= 100)
		{
			if(firstTime)
			{	
				//System.out.println("if"); 
				result.add(processes.get(0));
				processes.get(0).setRunTime(processes.get(0).getRunTime()-1);
				runtime++;
				//System.out.println("if " + runtime); 
				
				if(processes.get(0).getRunTime() <= 0)
				{
					processes.remove(0);
				}
				
				else
				{
					remainingJob.add(processes.get(0));
					processes.remove(0);
				}
				
				if(remainingJob.size()!=0)
				{
					firstTime=false;
				}
			}
			
			else if(processes.get(processes.size()-1).getArrivalTime()<runtime)
			{
				//System.out.println("else if"); 
				float leastRT =1000;
				int position = 0;
				for(int i =0; i<remainingJob.size();i++)
				{
					if(remainingJob.get(i).getRunTime()<leastRT)
					{
						leastRT = remainingJob.get(i).getRunTime();
						position = i; 
					}
				}
				runtime++;
				//System.out.println("else if " + runtime);
				
				while(remainingJob.get(position).getRunTime()>0)
				{
					result.add(remainingJob.get(position));
					remainingJob.get(position).setRunTime(remainingJob.get(position).getRunTime()-1);
					runtime++;
				}
						
				remainingJob.remove(position);
			}
			
			else
			{
			
				//System.out.println("else"); 
				for(int i = 0; i<processes.size();i++)
				{
					if(processes.get(i).getArrivalTime() <= runtime)
					{
						remainingJob.add(processes.get(i));
						processes.remove(processes.get(i));
					}
				}
				
				
				float leastRT =1000;
				int position = 0;
				for(int i =0; i<remainingJob.size();i++)
				{
					if(remainingJob.get(i).getRunTime()<leastRT)
					{
						leastRT = remainingJob.get(i).getRunTime();
						position = i; 
					}
				}
				
				
				result.add(remainingJob.get(position));
				remainingJob.get(position).setRunTime(remainingJob.get(position).getRunTime()-1);
				runtime++;
				
				if(remainingJob.get(position).getRunTime()<=0)
				{
					remainingJob.remove(position);
				}
				
				if(remainingJob.size()==0) firstTime = true;
			}
			
		}
		
		return result;
	}
	
	//End of jagrut
	
	
	
	public ArrayList<Process> srt() {
		ArrayList<Process> result = new ArrayList<Process>();
		ArrayList<Process> remainingJob = new ArrayList<Process>();
		int counter = 0, processCounter = 0;
		
		remainingJob.add(processes.get(processCounter));
		processCounter++;
		Process minProcess = remainingJob.get(0);
		while(counter <= 100) {
			int temp =  (int) Math.ceil(processes.get(processCounter).getArrivalTime());
			while(temp < counter && processCounter < processes.size() - 1) {
				remainingJob.add(processes.get(processCounter));
				processCounter++;
				temp = (int) Math.ceil(processes.get(processCounter).getArrivalTime());
			}
			if(remainingJob.size() > 2) {
				minProcess = Collections.min(remainingJob, new Comparator<Process>(){
				public int compare(Process p1, Process p2) {
				    	return  (int) (p1.getRunTime() - p2.getRunTime());
				    }
				}); 
			}
			
			result.add(minProcess);
			float currentRunTime = minProcess.getRunTime() - 1;
			minProcess.setRunTime(currentRunTime);
			if(currentRunTime < 0) {
				remainingJob.remove(minProcess);
			}
			
			counter++;
		}
		
		//After we are done with 100 counts, only process remaining jobs. 
		while(remainingJob.size() > 0) {
			minProcess = Collections.min(remainingJob, new Comparator<Process>(){
				public int compare(Process p1, Process p2) {
				    	return  (int) (p1.getRunTime() - p2.getRunTime());
				    }
			});
			
			result.add(minProcess);
			float currentRunTime = minProcess.getRunTime() - 1;
			minProcess.setRunTime(currentRunTime);
			if(currentRunTime < 0) {
				remainingJob.remove(minProcess);
			}
		}
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
			int index = firstIndex(result, entry.getKey());
			sum += ((entry.getValue() + 1) - index);
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
	
	private int firstIndex(ArrayList<Process> result, char c) {
		for(int i = 0; i < result.size(); i++) {
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
		avg = (map.size() + 0.0) / result.size();
		return avg;
	}

}
