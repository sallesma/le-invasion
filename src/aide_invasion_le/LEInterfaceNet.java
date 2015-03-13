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

	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public LEInterfaceNet() {
		// TODO Auto-generated constructor stub
	}
	
	public void connection(String serverAdr, int port)
	{
		try {
			InetAddress ServeurAdresse= InetAddress.getByName(serverAdr);
	        System.out.println("L'adresse du serveur est : "+ServeurAdresse+ " ; Port " + port);
		    socket = new Socket(ServeurAdresse,port);	
		    System.out.println("Demande de connexion");
	
		    in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream());
		    
		    Thread t3 = new Thread(new Reception(in));
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
		
		byte[] name;
		try {
			name = pseudo.getBytes("ISO8859-1");
		byte[] pass = pwd.getBytes("ISO8859-1");
		int size = name.length + 1 + pass.length + 1;
		byte[] out = new byte[size + 2];

		//java primitives are signed, we're receiving unsigned. bit shift!
		out[0] = (byte)140;
		out[1] = (byte)((size >> 8) & 0xFF); 
		out[2] = (byte)((size) & 0xFF);

		int offset = 3;
		System.arraycopy(name, 0, out, offset, name.length);
		offset += name.length + 1;
		out[offset - 1] = " ".getBytes("ISO8859-1")[0];
		System.arraycopy(pass, 0, out, offset, pass.length);
		
		send(out);
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startHeart_Beat()
	{
		System.out.println("Heart Beat");
		TimerTask task = new TimerTask()
		{
			@Override
			public void run() 
			{
				byte[] dat = new byte[3];
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
		try {
			byte[] msg = message.getBytes("ISO8859-1");
			byte[] outByteMessage = new byte[msg.length + 4];
			int size = outByteMessage.length - 2;
			outByteMessage[1] = (byte)((size >> 8) & 0xFF);
			outByteMessage[2] = (byte)((size) & 0xFF);
			outByteMessage[3] = 1;// the channel number
			System.arraycopy(msg, 0, outByteMessage, 4, msg.length);
			send(outByteMessage);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(byte[] data)
	{
		System.out.println("Bytes sended : " + data);
		out.println(data);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
