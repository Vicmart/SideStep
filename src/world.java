import java.awt.Color;
import java.util.*;

public class world {
	List<dObject> dObjects = new ArrayList<dObject>();
	List<sObject> sObjects = new ArrayList<sObject>();
	List<player> Players = new ArrayList<player>();
	List<biome> Biomes = new ArrayList<biome>();
	List<notification> Notifications = new ArrayList<notification>();
	List<sun> Suns = new ArrayList<sun>();
	int currentBiome = 0;
    int biomeSize = 10000;
    int boxheight = 0;
	int boxwidth = 0;
	int[] bg = new int[3];
	int tutorials = 0;
	double biomeStart = 0;
    double distanceLeft = 0;
	double time = 0.0;
	double lastChance = -1;
	boolean gameOver = false;
	boolean pause = false;
	boolean[] keys = new boolean[1000];
	
	public world(){	
		this.distanceLeft = (this.biomeSize*5) + 2200;
	}
	public void addObject(dObject a){
		dObjects.add(a);
	}
	public void addObject(sObject a){
		sObjects.add(a);
	}
	public void addPlayer(player a){
		Players.add(a);
	}
	public void addBiome(biome a){
		Biomes.add(a);
	}
	public void addNotification(notification a){
		Notifications.add(a);
	}
	public void addSun(sun a){
		Suns.add(a);
	}
}
