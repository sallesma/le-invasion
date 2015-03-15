package aide_invasion_le;

import java.io.DataInputStream;
import java.io.EOFException;


public class Reception implements Runnable {

	private LEInterfaceNet leInterf;
	private DataInputStream in;
	
	public Reception(DataInputStream in, LEInterfaceNet leInterf){
		
		this.leInterf = leInterf;
		this.in = in;
	}
	
	public void run() {
		while (true) {
			try {
				Byte type = 0;
				while (true) {
					try {
						type = in.readByte();
						if (type != null)
							break;
					} catch (EOFException e) {}
				}
				int length = in.readUnsignedByte() + in.readUnsignedByte() * 256;
				int dataLength = length - 1;
				byte[] data = new byte[dataLength];
				for(int i = 0 ; i < dataLength ; i++){
					data[i] = in.readByte();
				}
				leInterf.reception(type, data);
			} catch (Exception e) {
				e.printStackTrace();
				if("Socket closed".equals(e.getMessage()))
					break;
			}
		}
	}
}