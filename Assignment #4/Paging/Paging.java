import java.util.ArrayList;


public class Paging {
	private static ArrayList<Integer> pages;
     static double FIFOHitRatio = 0.0; 
     static double LRUHitRatio = 0.0; 
     static double LFUHitRatio = 0.0; 
     static double MFUHitRatio = 0.0; 
     static double RandomPickHitRatio = 0.0; 
	public static void main (String[] args) {

      for(int i=0;i<5;i++)
      {
         pages = new ArrayList<Integer>();
         initialize();
         System.out.println("SIMULATION # " + (i+1));
         //Run FIFO Algorithm
         System.out.println("\n############################################################################\n");
         System.out.println("\nFIFO ALGORITHM SIMULATION\n");
         runFIFO();
         //Run LRU Algorithm 
         System.out.println("\n############################################################################\n");
         System.out.println("\nLRU ALGORITHM SIMULATION\n");
         runLRU();
         //Run LFU Algorithm 
         System.out.println("\n############################################################################\n");
         System.out.println("\nLFU ALGORITHM SIMULATION\n");
         runLFU();
         //Run MFU Algorithm.
         System.out.println("\n############################################################################\n");
         System.out.println("\nMFU ALGORITHM SIMULATION\n");
         runMFU();
         //Run randomPick Algorithm 
         System.out.println("\n############################################################################\n");
         System.out.println("\nRandom Pick ALGORITHM SIMULATION\n");
         runRandomPick();
      }
      
		System.out.println("\n############################################################################\n");
		System.out.println("\nSTATISTICS FOR EACH ALGORITHMS\n");
      
      System.out.println("Average Hit Ratio for FIFO : " + FIFOHitRatio/5.0 +"\n") ;
      System.out.println("Average Hit Ratio for LRU : " + LRUHitRatio/5.0 +"\n") ;
      System.out.println("Average Hit Ratio for LFU : " + LFUHitRatio/5.0 +"\n") ;
      System.out.println("Average Hit Ratio for MFU : " + MFUHitRatio/5.0 +"\n") ;
      System.out.println("Average Hit Ratio for Random Pick : " + RandomPickHitRatio/5.0 +"\n") ;

      
     }
   
   public static void runFIFO()
   {
         FIFO fifo = new FIFO(pages); 
         fifo.run();
         System.out.printf("HIT RATIO: %d\n", fifo.getHitRatio());
         FIFOHitRatio += fifo.getHitRatio();
   }
	public static void runLFU()
   {
         LFU lfu = new LFU(pages); 
         lfu.run(); 
         System.out.printf("HIT RATIO: %.2f\n", lfu.getHitRatio() * 100);
         LFUHitRatio +=  (lfu.getHitRatio() * 100);

   }
   public static void runMFU()
   {
        MFU mfu = new MFU(pages);
		  mfu.run();
		  System.out.printf("HIT RATIO: %.2f\n", (mfu.getHitRatio() * 100));
        MFUHitRatio += (mfu.getHitRatio() * 100); 
   }
   public static void runRandomPick()
   {
      randomPick rp =  new randomPick(pages); 
      rp.run(); 
      System.out.printf("HIT RATIO: %d\n", rp.getHitRatio());
      RandomPickHitRatio += rp.getHitRatio();
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