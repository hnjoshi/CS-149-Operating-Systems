import java.util.ArrayList;

/**
 * This class will implement Most Frequently Used Paging Algorithm.
 * @author Jay 
 */
public class MFU {
	private ArrayList<Integer> pages;
	private int hitRatio, FRAME_SIZE;
	private Page[] frames;
	
	public MFU(ArrayList<Integer> p) {
		pages = p;
		hitRatio = 0;
		frames = new Page[4];
		FRAME_SIZE = 4;
		for(int i = 0; i < FRAME_SIZE; i++) {
			frames[i] = new Page(-1, 0);
		}
	}
	
	public void run() {
		StringBuffer buffer = new StringBuffer();
		for(int i : pages) {
			buffer.append(String.format("Referenced Page: %d", i));
			buffer.append(String.format("%5s", ""));
			boolean found = searchInFrames(i);
			String event = "";
			if(found) {
				hitRatio++;
			} else {
				int evictIndex = getEvictIndex();
				int pageToEvict = frames[evictIndex].data;
				Page newPage = new Page(i, 0);
				frames[evictIndex] = newPage;
				
				if(pageToEvict == -1) {
					event = String.format("Inserted Page: %d", i);
				} else {
					event = String.format(" Evicted Page: %d", pageToEvict);
				}
			}
			String frameImage = printFrame();
			buffer.append(String.format("%s", frameImage));
			buffer.append(String.format("%5s", ""));
			buffer.append(event);
			updateUseCount();
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
	
	private void updateUseCount() {
		for(Page j : frames) {
			j.useCount++;
		}
	}
	
	private int getEvictIndex() {
		int index = 0, max = 0;
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