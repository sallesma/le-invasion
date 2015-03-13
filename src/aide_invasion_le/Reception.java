package aide_invasion_le;

import java.io.BufferedReader;
import java.io.IOException;


public class Reception implements Runnable {

	private BufferedReader in;
	private String message = null;
	private int nullnbr = 0;
	
	public Reception(BufferedReader in){
		
		this.in = in;
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
			if(nullnbr <= 3 && message == null)
				nullnbr++;
			
			if(nullnbr == 3)
				System.out.println("Reçu : more than 2 null in row");
			else if(nullnbr <= 2)
				System.out.println("Reçu : " + message);

			
		    } catch (IOException e) {
				
				//e.printStackTrace();
			}
		}
	}

}