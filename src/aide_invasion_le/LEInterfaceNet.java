package aide_invasion_le;

import java.util.Timer;
import java.util.TimerTask;

public class LEInterfaceNet{

	/**
	 * 
	 */

	public LEInterfaceNet() {
		// TODO Auto-generated constructor stub
	}
	
	public void connection()
	{


		startHeart_Beat();
	}
	
	public void startHeart_Beat()
	{
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				System.out.println("Heart_Beat");
			}	
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

}
