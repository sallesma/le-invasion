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
			/*byte[] pass = pwd.getBytes("ISO8859-1");
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
			
			send(out);*/
			
			//[LOG_IN (140)][LENGTH]USERNAME[SPACE]PASSWORD[NULL]
			//[LOG_IN (140)][LENGTH]USERNAME[SPACE]PASSWORD[NULL]
			//byte[] outByteMessage = {(byte)140,(byte)21,(byte)'t', (byte)'e', (byte)'s', (byte)'t', (byte)'_', (byte)'i', (byte)'n', (byte)'t', (byte)'e', (byte)'r', (byte)'f', (byte)' ', (byte)'a', (byte)'z', (byte)'e', (byte)'r', (byte)'t', (byte)'y', 0};
				
			//Test unint8 length
			/*byte[] dat = new byte[21];
			dat[0] = (byte)140;
			dat[1] = 21;
			dat[2] = 't';
			dat[3] = 'e';
			dat[4] = 's';
			dat[5] = 't';
			dat[6] = '_';
			dat[7] = 'i';
			dat[8] = 'n';
			dat[9] = 't';
			dat[10] = 'e';
			dat[11] = 'r';
			dat[12] = 'f';
			dat[13] = ' ';
			dat[14] = 'a';
			dat[15] = 'z';
			dat[16] = 'e';
			dat[17] = 'r';
			dat[18] = 't';
			dat[19] = 'y';
			dat[20] = 0;*/
			
			//Test unint16 length
			byte[] dat = new byte[22];
			dat[0] = (byte)140;
			dat[1] = 22;
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
		try {
			System.out.println("Bytes sended : " + data[0]+ ":"  + data[1]+ ":"  + data[2] + " : " + new String(data, "ISO8859-1") + " : "  + data[data.length-3] + ":"  + data[data.length-2]+ ":"  + data[data.length-1]);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.println(data);
		//out.print(data);
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
