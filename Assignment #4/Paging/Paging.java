import java.util.ArrayList;


public class Paging {
	private static ArrayList<Integer> pages;
	
	public static void main (String[] args) {
		pages = new ArrayList<Integer>();
		initialize();
		printPages();
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