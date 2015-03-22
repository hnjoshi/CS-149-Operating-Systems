import java.util.ArrayList;

import org.omg.CORBA.FREE_MEM;

public class LRU {
	
	

	
	/**
	 * This class will implement Least Recently Used Paging Algorithm. 
	 */
		private ArrayList<Integer> pages;
		private int hitRatio, FRAME_SIZE;
		private Page[] frames;
		
		public LRU(ArrayList<Integer> p) {
			pages = p;
			hitRatio = 0;
			frames = new Page[4];
			FRAME_SIZE = 4;
			for(int i = 0; i < FRAME_SIZE; i++) {
				frames[i] = new Page(-1, 0);
			}
		}
		//1            100 - 1             //99
		//1,2          100 - 1, 100 - 2    //99, 98
		//1,2,3        100 - 1, 100 - 2, 100 - 3   //99, 98, 97 
		//1,2,3,4      100 - 1, 100 - 2, 100 - 3, 100 - 4   //99, 98, 97, 96 
		//1,2,3,4,5    100 - 1, 100 - 2, 100 - 3 , 100 - 4, 100 -5 //99, 98, 97, 96, 95 
		public void run() {
			StringBuffer buffer = new StringBuffer();
			int evictCounter = 0;
			for(int i : pages) {
				buffer.append(String.format("Referenced Page: %d", i));
				buffer.append(String.format("%5s", ""));
				boolean found = searchInFrames(i);
				String event = "";
				if(found) {
					hitRatio++;
					int index = getFrameIndex(i);
					frames[index].useCount = 100 - evictCounter;
				} else {
					int evictIndex = getEvictIndex();
					int pageToEvict = frames[evictIndex].data;
					Page newPage = new Page(i, 100-evictCounter);
					frames[evictIndex] = newPage;
					if(pageToEvict == -1) {
						event = String.format("Inserted Page: %d", i);
					} else {
						event = String.format(" Evicted Page: %d", pageToEvict);
					}
				}
				evictCounter++;
				String frameImage = printFrame();
				buffer.append(String.format("%s", frameImage));
				buffer.append(String.format("%5s", ""));
				buffer.append(event);
				//updateUseCount();
				System.out.println(buffer.toString());
				buffer.setLength(0);
			}
		}
		
		public double getHitRatio() {
			return ((hitRatio + 0.0) / pages.size());
		}
		
		private boolean searchInFrames(int i) {	
			for(Page j : frames) {
				if(j.data == i) {
					return true;
				}
			}
			return false;
		}
		
		private int getFrameIndex(int i) {	
			for(int j = 0; j < FRAME_SIZE; j++) {
				if(frames[j].data == i) {
					return j;
				}
			}
			return -1;
		}
		/**
		private void updateUseCount() {
			for(Page j : frames) {
				j.useCount++;
			}
		}**/
		
		
		//As a process is added update its counter
		//When the existing process is to be inserted, Increase its counter
		//When new Process comes in and its not existing 
		//then replace it with least counter
		
		private int getEvictIndex() {
			int index = 0, max = Integer.MIN_VALUE;
			for(int i = 0; i < FRAME_SIZE; i++) {
				if(frames[i].data == -1) {
					return i;
				}
				
				if(max < frames[i].useCount) {
					max = frames[i].useCount;
					index = i;
				}
			}
			return index;
		}
		
		
		private String printFrame() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Page Frames: ");
			for(Page p : frames) {
				buffer.append(String.format("%2d", p.data));
				buffer.append(' ');
			}
			return buffer.toString();
		}
		
		class Page {
			int data;
			int useCount; 
			
			Page(int data, int useCount) {
				this.data = data;
				this.useCount = useCount;
			}
		}
	}
