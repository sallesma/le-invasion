package aide_invasion_le;

import java.io.BufferedReader;
import java.io.IOException;


public class Reception implements Runnable {

	private LEInterfaceNet leInterf;
	private BufferedReader in;
	
	public Reception(BufferedReader in, LEInterfaceNet leInterf){
		
		this.leInterf = leInterf;
		this.in = in;
	}
	
	public void run() {
		while (true) {
			try {
				String message = in.readLine();
				if (message != null)
					leInterf.reception(message.toCharArray());
			} catch (IOException e) {
				if(e.getMessage().equals("Socket closed"))
					break;
				e.printStackTrace();
			}
		}
	}
}