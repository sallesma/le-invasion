package aide_invasion_le;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class LEInterfaceNet{

	private Socket socket;
	private DataInputStream in;
	private BufferedOutputStream out;
	private Timer timer;

	final static private int LOG_IN_TYPE = 140;
	final static private int HEART_BEAT = 14;
	final static private int PING = 13;
	final static private int PONG = 11;
	final static private int PING_REQUEST = 60;
	final static private int LOG_IN_OK = 250;
	final static private int LOG_IN_NOT_OK = 251;
	final static private int NEW_MINUTE = 5;
	final static private int GET_ACTIVE_SPELL_LIST = 45;
	final static private int SYNC_CLOCK = 4;
	final static private int YOU_ARE = 3;
	final static private int CHANGE_MAP = 7;
	final static private int HERE_YOUR_INVENTORY = 19;
	final static private int RAW_TEXT = 0;
	final static private int HERE_YOUR_STATS = 18;
	
	public void connection(String serverAdr, int port)
	{
		try {
			InetAddress ServeurAdresse= InetAddress.getByName(serverAdr);
	        System.out.println("L'adresse du serveur est : "+ServeurAdresse+ " ; Port " + port);
		    socket = new Socket(ServeurAdresse,port);	
		    System.out.println("Demande de connexion");
	
		    in = new DataInputStream (socket.getInputStream());
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
			dat[i+j] = (byte)pwd.charAt(j);
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
	
	public void sendMessage(int channel, String message)
	{
		System.out.println("Message : " + channel + " : " + message);
		
		int lengt = message.length() + 3;
		int j=0;
		
		byte[] dat = new byte[255];
		dat[0] = (byte)RAW_TEXT;
		dat[1] = (byte)(lengt);
		dat[2] = 0;
		dat[3] = (byte)channel;
		
		for (j=0; j<message.length() ; j++)
		{
			dat[j+4] = (byte)message.charAt(j);
		}
			
		send(dat);
	}
	
	public void send(byte[] data)
	{
		System.out.println("Sent : " + new String(data) + " (");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print(";" + data[i]);
		}
		System.out.println(")\n\n");
		
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
	
	public void reception(byte type, byte[] data)
	{
		System.out.println("RECEIVED");
		System.out.println("Type: " + type);
		
		if (type==PONG)
			System.out.println("PONG");
		else if (type==PING_REQUEST)
		{
			System.out.println("PING_REQUEST");
			//send(data);
		} else if (type == LOG_IN_OK)
			System.out.println("LOGIN OK");
		else if (type == LOG_IN_NOT_OK)
			System.out.println("LOGIN fail");
		else if (type == NEW_MINUTE)
			System.out.println("NEW_MINUTE");
		else if (type == GET_ACTIVE_SPELL_LIST)
			System.out.println("GET_ACTIVE_SPELL_LIST");
		else if (type == SYNC_CLOCK)
			System.out.println("SYNC_CLOCK");
		else if (type == YOU_ARE)
			System.out.println("YOU_ARE");
		else if (type == CHANGE_MAP)
			System.out.println("CHANGE_MAP");
		else if (type == HERE_YOUR_INVENTORY)
			System.out.println("HERE_YOUR_INVENTORY");
		else if (type == RAW_TEXT)
			System.out.println("RAW_TEXT");
		else if (type == HERE_YOUR_STATS)
			System.out.println("HERE_YOUR_STATS");
		
		System.out.print("ByteData: ");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print(";" + (int)data[i]);
		}
		System.out.println();
		System.out.print("Textdata: ");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print((char)data[i]);
		}
		System.out.println("\n\n");
	}

}
