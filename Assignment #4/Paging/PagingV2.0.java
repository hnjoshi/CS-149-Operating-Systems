import java.util.ArrayList;


public class Paging {
	private static ArrayList<Integer> pages;
	
	public static void main (String[] args) {
		pages = new ArrayList<Integer>();
      for(int i=0;i<5;i++)
      {
         initialize();
         System.out.println("SIMULATION # " + i);
         //Run FIFO Algorithm
         runFIFO();
         //Run LRU Algorithm 
         runLRU();
         //Run LFU Algorithm 
         runLFU();
         //Run MFU Algorithm.
         runMFU();
         //Run randomPick Algorithm 
         runRandomPick();
      }
     }
   
   public static void runFIFO()
   {
         FIFO fifo = new FIFO(pages); 
         fifo.run();
         System.out.printf("HIT RATIO: %d\n", fifo.getHitRatio());
   }
	public static void runLFU()
   {
         LFU lfu = new LFU(pages); 
         lfu.run(); 
         System.out.printf("HIT RATIO: %.2f\n", lfu.getHitRatio());

   }
   public static void runMFU()
   {
        MFU mfu = new MFU(pages);
		  mfu.run();
		  System.out.printf("HIT RATIO: %.2f\n", (mfu.getHitRatio() * 100));
   }
   public static void runRandomPick()
   {
      randomPick rp =  new randomPick(pages); 
      rp.run(); 
      System.out.printf("HIT RATIO: %d\n", rp.getHitRatio());
   }
   public static void runLRU()
   {
      
   }
   public static void initialize() 
	{
		for (int i=0; i<100;i++)
		{
			if(i==0)
			{
				int temp = (int) (Math.random() * 10);
				pages.add(temp); 
			}
			else
			{
				int temp = (int)(Math.random()*10);
							
				if(temp>=0 && temp<7)
				{
					int delta  = (int) ((Math.random() * 3) - 1);
					int val = (temp + delta);
					pages.add(val);	
				}
				else
				{
					int delta  = (int) ((Math.random() * 7) + 2);
					int val = (temp + delta) % 10;
					pages.add(val); 
				}
			}
		}
		
	}
	
	public static void printPages()
	{
		for(Integer i: pages)
		{
			System.out.print(i +" ");
		}
	}
}