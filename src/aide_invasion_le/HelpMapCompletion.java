package aide_invasion_le;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HelpMapCompletion {
	
	 String fichier ="data\\maps.map";
	 ArrayList<String[]> aListMaps = new ArrayList<String[]>();
	 
	 String map1 = "toto";
	 int number = 0;
	 int size = 0;

	public HelpMapCompletion() {
		// TODO Auto-generated constructor stub

		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			String str[]= new String[4];
			while ((ligne=br.readLine())!=null){
				str=ligne.split(";");
				aListMaps.add(str);
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public void setMap(String mapName) {
		// TODO Auto-generated method stub
		map1 = mapName;
		
		for(int i = 0; i < aListMaps.size(); i++)
	    {
			if (mapName.equals(aListMaps.get(i)[2]))
			{
				size = Integer.parseInt(aListMaps.get(i)[1]);
				number = Integer.parseInt(aListMaps.get(i)[0]);
			}
	    }   
		
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return number;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}

}
