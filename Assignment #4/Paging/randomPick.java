
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jagrut
 */
public class randomPick 
{
     private int hitRatio = 0;
   private int frame_size = 4;
   private ArrayList<Integer> pages;
   private ArrayList<Integer> frames;

   public randomPick(ArrayList<Integer> page) {
      pages = page;
      frames = new ArrayList<Integer>();
      for (int i = 0; i < 4; i++) {
         frames.add(i, -1);
      }
   }

   public void run() {
      StringBuffer buffer = new StringBuffer();
      int evictIndex = (int) (Math.random()*4);
      for (int i : pages) {
         buffer.append(String.format("Referenced Page: %d", i));
         buffer.append(String.format("%5s", ""));
         String event = "";
         if (frames.contains(i)) {
            hitRatio++;
         } else {
            
            if (frames.get(evictIndex) == -1) {
               event = String.format("Inserted Page: %d", i);
            } else {
               event = String.format(" Evicted Page: %d", frames.get(evictIndex));
            }
            
            frames.set(evictIndex, i);
            evictIndex++;
            if(evictIndex>3)
            {
               evictIndex = 0;
            }
         }
         String frameImage =  printFrame();
         buffer.append(String.format("%s", frameImage));
			buffer.append(String.format("%5s", ""));
			buffer.append(event);
         System.out.println(buffer.toString());
			buffer.setLength(0);
         
      }
   }
   
   public int getHitRatio()
   {
      return hitRatio;
   }

   private String printFrame() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("Page Frames: ");
      for (Integer p : frames) {
         buffer.append(String.format("%2d", p));
         buffer.append(' ');
      }
      return buffer.toString();
   }
   
}
