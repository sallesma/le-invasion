package aide_invasion_le;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class LEInterfaceNet{

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	final static private int LOG_IN_TYPE = 140;
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
		    out = new PrintWriter(socket.getOutputStream());
		    
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
		
		char[] dat = new char[255];
		dat[0] = (char)LOG_IN_TYPE;
		dat[1] = 20;
		dat[2] = 0;
		dat[3] = 't';
		dat[4] = 'e';
		dat[5] = 's';
		dat[6] = 't';
		dat[7] = '_';
		dat[8] = 'i';
		dat[9] = 'n';
		dat[10] = 't';
		dat[11] = 'e';
		dat[12] = 'r';
		dat[13] = 'f';
		dat[14] = ' ';
		dat[15] = 'a';
		dat[16] = 'z';
		dat[17] = 'e';
		dat[18] = 'r';
		dat[19] = 't';
		dat[20] = 'y';
		dat[21] = 0;
			
		send(dat);
	}
	
	public void ping() {
		char[] dat = new char[7];
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
		System.out.println("Heart Beat");
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				char[] dat = new char[3];
				 dat[0] = 14;
				 dat[1] = 1;
				send(dat);
			}	
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 25000);
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

			send(outByteMessage);
	}
	
	public void send(char[] data)
	{
		System.out.println("Sent : " + new String(data) + " (");
		for(int i = 0; i<data.length;i++)
		{
			System.out.print(";" + (int)data[i]);
		}
		System.out.println(")");
		
		out.write(new String(data), 0, data.length);
	    out.flush();
	}
	
	public void close()
	{
		System.out.println("Close Socket");
        try {
        	if (socket != null && !socket.isClosed())
        		socket.close();
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
			send(data);
		} else if (data[0]==LOG_IN_OK) {
			System.out.println("Login OK");
		} else if (data[0]==LOG_IN_NOT_OK) {
			System.out.println("Login fail");
		}
		
		System.out.println("\n\n\n");
	}

}
