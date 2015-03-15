package aide_invasion_le;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class LEInterfaceNet{

	private Socket socket;
	private BufferedReader in;
	private BufferedOutputStream out;
	private Timer timer;

	final static private int LOG_IN_TYPE = 140;
	final static private int HEART_BEAT = 14;
	final static private int PING = 13;
	final static private int PONG = 11;
	final static private int PING_REQUEST = 60;
	final static private int LOG_IN_OK = 250;
	final static private int LOG_IN_NOT_OK = 251;
	
	public void connection(String serverAdr, int port)
	{
		try {
			InetAddress ServeurAdresse= InetAddress.getByName(serverAdr);
	        System.out.println("L'adresse du serveur est : "+ServeurAdresse+ " ; Port " + port);
		    socket = new Socket(ServeurAdresse,port);	
		    System.out.println("Demande de connexion");
	
		    in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		    out = new BufferedOutputStream(socket.getOutputStream());
		    
		    Thread t3 = new Thread(new Reception(in, this));
			t3.start();
		    
		}catch (UnknownHostException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void login(String pseudo, String pwd)
	{
		System.out.println("Login : " + pseudo + " " + pwd);
		
		int lengt = pseudo.length() + pwd.length() + 3;
		int i=3, j=0;
		
		byte[] dat = new byte[255];
		dat[0] = (byte)LOG_IN_TYPE;
		dat[1] = (byte)(lengt);
		dat[2] = 0;
		
		for (j=0; j<pseudo.length() ; j++)
		{
			dat[i+j] = (byte)pseudo.charAt(j);
		}
		i=i+j+1;
		dat[i] = ' ';
		for (j=0; j<pwd.length() ; j++)
		{
			dat[i] = (byte)pwd.charAt(j);
		}
		i=i+j+1;
		dat[i] = 0;
			
		send(dat);
	}
	
	public void ping() {
		byte[] dat = new byte[7];
		dat[0] = PING;
		dat[1] = 4;
		dat[2] = 0;
		dat[3] = 1;
		dat[4] = 1;
		dat[5] = 1;
		dat[6] = 1;
		send(dat);
	}
	
	public void startHeart_Beat()
	{
		System.out.println("Start Heart Beat");
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				byte[] dat = new byte[3];
				 dat[0] = HEART_BEAT;
				 dat[1] = 1;
				 dat[2] = 0;
				send(dat);
			}	
		};
		
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 25000);
	}
	
	public void stopHeart_Beat()
	{
		System.out.println("Stop Heart Beat");
		timer.cancel();
	}
	
	public void sendMessage(String message)
	{
		System.out.println("Message : " + message);

			char[] msg = message.toCharArray();
			char[] outByteMessage = new char[msg.length + 4];
			int size = outByteMessage.length - 2;
			outByteMessage[1] = (char)((size >> 8) & 0xFF);
			outByteMessage[2] = (char)((size) & 0xFF);
			outByteMessage[3] = 1;// the channel number
			System.arraycopy(msg, 0, outByteMessage, 4, msg.length);

			//send(outByteMessage);
	}
	
	public void send(byte[] data)
	{
		System.out.println("Sent : " + new String(data) + " (");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print(";" + data[i]);
		}
		System.out.println(")");
		
	    try {
	    	out.write(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		System.out.println("Close Socket");
        try {
        	if (socket != null && !socket.isClosed()) {
        		socket.close();
        		this.stopHeart_Beat();
        	}
        	else
        		System.out.println("Unable to close socket");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reception(char[] data)
	{
		System.out.println("Reçu : " + new String(data) + " (");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print(";" + (int)data[i]);
		}
		System.out.println(")");
		
		if (data[0]==PONG)
		{
			System.out.println("PONG");
		} else if (data[0]==PING_REQUEST)
		{
			System.out.println("PING_REQUEST");
			//send(data);
		} else if (data[0]==LOG_IN_OK) {
			System.out.println("Login OK");
		} else if (data[0]==LOG_IN_NOT_OK) {
			System.out.println("Login fail");
		}
		
		System.out.println("\n\n");
	}

}
