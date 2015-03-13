package aide_invasion_le;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HelpMapCompletion {
	
	 String mapsFilePath = Paths.get("data", "maps.map").toString();
	 Map<String, int[]> maps;

	public HelpMapCompletion() {
		maps = new HashMap<String, int[]>();
		try{
			InputStream ips=new FileInputStream(mapsFilePath); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			String str[]= new String[4];
			while ((ligne=br.readLine())!=null){
				str=ligne.split(";");
				String name = str[2];
				int id = Integer.parseInt(str[0]);
				int size = Integer.parseInt(str[1]);
				maps.put(name, new int[]{id, size});
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public int getMapId(String mapName) {
		return maps.get(mapName)[0];
	}

	public int getMapSize(String mapName) {
		return maps.get(mapName)[1];
	}

}
