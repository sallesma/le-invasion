package aide_invasion_le;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MapsManager {
	
	private String mapsListPath = Paths.get("data", "maps.map").toString();
	private File mapImagesFolder = new File(Paths.get("images", "maps").toString());
	private Map<String, int[]> maps;

	public MapsManager() {
		maps = new HashMap<String, int[]>();
		try{
			InputStream ips=new FileInputStream(mapsListPath); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String line;
			String str[]= new String[4];
			while ((line=br.readLine())!=null){
				try {
					str=line.split(";");
					String name = str[2];
					int id = Integer.parseInt(str[0]);
					int size = Integer.parseInt(str[1]);
					maps.put(name, new int[]{id, size});
				} catch (NumberFormatException e) {
					System.out.println("Ignoring line in maps because of bad number format: " + line);
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String[] getMapNames() {
		return maps.keySet().toArray(new String[maps.size()]);
	}

	public int getMapId(String mapName) {
		return maps.get(mapName)[0];
	}

	public int getMapSize(String mapName) {
		return maps.get(mapName)[1];
	}

	public Path getMapFilePath(String mapName) {
		return Paths.get( this.mapImagesFolder.getAbsolutePath(), mapName);
	}

}
