import java.awt.Color;


public class terrainAlgorithm {
	private static int max = 10;
	
	public static world main(world Worldly){
		for(int i=5;i<Worldly.sObjects.size();i++){
			if(i%Worldly.biomeSize==0){
				Worldly.currentBiome++;
			}
			if(Worldly.currentBiome>=Worldly.Biomes.size()){
				Worldly.currentBiome=0;
			}
			max = Worldly.Biomes.get(Worldly.currentBiome).steepness;
			if(Worldly.sObjects.get(i-1).y>Worldly.sObjects.get(i-2).y){
				Worldly.sObjects.get(i).y = mainWindow.random((int)Worldly.sObjects.get(i-1).y,(int)Worldly.sObjects.get(i-1).y+max);
			}else if(Worldly.sObjects.get(i-1).y<Worldly.sObjects.get(i-2).y){
				Worldly.sObjects.get(i).y = mainWindow.random((int)Worldly.sObjects.get(i-1).y-max,(int)Worldly.sObjects.get(i-1).y);
			}else{
				Worldly.sObjects.get(i).y = mainWindow.random((int)Worldly.sObjects.get(i-1).y-max,(int)Worldly.sObjects.get(i-1).y+max);	
			}
			if(mainWindow.random(1,10000)<Worldly.Biomes.get(Worldly.currentBiome).gaps&&i>200&&i<Worldly.sObjects.size()-1000&&Worldly.sObjects.get(i-9).unstable==false){
				for(int j=0;j<mainWindow.random(10, 10 + Worldly.Biomes.get(0).gapwidth);j++){
					Worldly.sObjects.get(i + j).unstable = true;
				}
			}
			int[] bio = Worldly.Biomes.get(Worldly.currentBiome).fg;
			Worldly.sObjects.get(i).color = new Color(mainWindow.random(bio[0], bio[1]),mainWindow.random(bio[2], bio[3]), mainWindow.random(bio[4],bio[5]));
		}
		return Worldly;
	}
}
