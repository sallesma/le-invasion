package aide_invasion_le;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LEInterfaceNet{

	private Socket socket;
	private DataInputStream in;
	private BufferedOutputStream out;
	private Timer heartBeatTimer;

	final static private byte LOG_IN_TYPE = (byte) 140;
	final static private byte HEART_BEAT = 14;
	final static private byte PING = 13;
	final static private byte PONG = 11;
	final static private byte PING_REQUEST = 60;
	final static private byte LOG_IN_OK = (byte) 250;
	final static private byte LOG_IN_NOT_OK = (byte) 251;
	final static private byte NEW_MINUTE = 5;
	final static private byte GET_ACTIVE_SPELL_LIST = 45;
	final static private byte SYNC_CLOCK = 4;
	final static private byte YOU_ARE = 3;
	final static private byte CHANGE_MAP = 7;
	final static private byte HERE_YOUR_INVENTORY = 19;
	final static private byte RAW_TEXT = 0;
	final static private byte HERE_YOUR_STATS = 18;
	
	final static private int CHECK_INVASION = 1;
	private int check_order = 0;
	
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
		String stringData = pseudo + " " + pwd;
		System.out.println("Login : " + stringData);
		
		byte[] data = new byte[255];
		for (int i=0; i<stringData.length() && i<254 ; i++)
		{
			data[i] = (byte)stringData.charAt(i);
		}
		data[stringData.length()] = 0;
			
		send(LOG_IN_TYPE, data);
	}
	
	public void ping() {
		byte[] dat = new byte[4];
		dat[0] = 1;
		dat[1] = 1;
		dat[2] = 1;
		dat[3] = 1;
		send(PING, dat);
	}
	
	public void startHeart_Beat()
	{
		System.out.println("Start Heart Beat");
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				send(HEART_BEAT, null);
			}	
		};
		
		heartBeatTimer = new Timer();
		heartBeatTimer.scheduleAtFixedRate(task, 0, 25000);
	}
	
	public void stopHeart_Beat()
	{
		System.out.println("Stop Heart Beat");
		heartBeatTimer.cancel();
	}
	
	public void sendRawText(String message)
	{
		System.out.println("Message : " + message);
		
		byte[] data = new byte[Math.min(message.length(), 255)];
		for (int i=0; i<data.length ; i++)
		{
			data[i] = (byte)message.charAt(i);
		}
		
		send(RAW_TEXT, data);
	}
	
	public void send(byte type, byte[] data)
	{
		int dataLength = 0;
		if (data != null)
			dataLength = data.length;
		byte[] message = new byte[dataLength+3];

		message[0] = type;
		message[1] = (byte) ((dataLength+1) % 256);
		message[2] = (byte) ((dataLength+1) / 256);
		
		if(data != null) {
			for (int i=0; i<data.length ; i++)
			{
				message[i+3] = data[i];
			}
		}
		
		System.out.println("Sent : " + new String(message) + " (");
		for(int i = 0; i<message.length;i++)
		{
			System.out.print(";" + message[i]);
		}
		System.out.println(")\n\n");
		
	    try {
	    	out.write(message);
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
			send(PING_REQUEST, data);
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
		{
			System.out.println("RAW_TEXT");
			
			if (check_order==CHECK_INVASION)
				parse_check_inva(data);
		}
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

	private void parse_check_inva(byte[] data) {
		// TODO Auto-generated method stub
		String s = new String(data);
		
		Pattern pattern = Pattern.compile("encore un (.)+ (automatique|périssable|ponctuel|permanent) en : ([0-9])+, ([0-9])+, ([0-9])+.$");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
		   System.out.println(matcher.group(1)+ Integer.parseInt(matcher.group(3))+  Integer.parseInt(matcher.group(4))+  Integer.parseInt(matcher.group(5)) );
		}
		else 
		{
		
			pattern = Pattern.compile("encore un ([0-9])+ monstres d'invasion.$");
			matcher = pattern.matcher(s);
			if (matcher.find()) {
			   System.out.println(Integer.parseInt(matcher.group(1)));
			   check_order = 0;
			}
		}
	}

}
