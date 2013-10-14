
public class collisionDetection {
	private static int holeSize = 15;
	
	public static world ballxGround(world Worldly, int camx, int camy){
		for(int c=0;c<Worldly.dObjects.size();c++){
			dObject comet = Worldly.dObjects.get(c);
			for(int d=0;d<=300;d++){
				sObject ground = Worldly.sObjects.get(d+camx/5);
				player play = Worldly.Players.get(0);
				if(comet.y+comet.width-40>ground.y&&comet.x>=ground.x&&comet.x<=ground.x+5&&ground.exists==true){
					if(comet.exploding==false){	
						ground.y+=holeSize;
						for(int e=1;e<=7;e++){
							try{
								Worldly.sObjects.get((d+camx/5)+e).y+=holeSize-e*2;
								Worldly.sObjects.get((d+camx/5)-e).y+=holeSize-e*2;
								if(Worldly.sObjects.get((d+camx/5)+e).unstable==true){Worldly.sObjects.get((d+camx/5)+e).yVel = 10;}
								if(Worldly.sObjects.get((d+camx/5)-e).unstable==true){Worldly.sObjects.get((d+camx/5)-e).yVel = 10;}
							}catch(java.lang.ArrayIndexOutOfBoundsException f){
							}
						}
					}
					comet.exploding=true;
				}
				if(comet.x>=ground.x&&comet.x<=ground.x+5&&ground.exists==true&&comet.exploding==true){
					if(Worldly.sObjects.get((d+camx/5)).unstable==true){Worldly.sObjects.get((d+camx/5)).y+= 0.05;}
					if(Worldly.sObjects.get((d+camx/5)).y>Worldly.boxheight+camy&&Worldly.sObjects. get((d+camx/5)).unstable==true){Worldly.sObjects.get((d+camx/5)).exists=false;}
					for(int e=1;e<=5;e++){
						try{
							if(Worldly.sObjects.get((d+camx/5)+e).unstable==true){Worldly.sObjects.get((d+camx/5)+e).y+= 0.05;}
							if(Worldly.sObjects.get((d+camx/5)-e).unstable==true){Worldly.sObjects.get((d+camx/5)-e).y+= 0.05;}
							if(Worldly.sObjects.get((d+camx/5)+e).y>Worldly.boxheight+camy&&Worldly.sObjects. get((d+camx/5)+e).unstable==true){Worldly.sObjects.get((d+camx/5)+e).exists=false;}
							if(Worldly.sObjects.get((d+camx/5)-e).y>Worldly.boxheight+camy&&Worldly.sObjects. get((d+camx/5)+e).unstable==true){Worldly.sObjects.get((d+camx/5)-e).exists=false;}
						}catch(java.lang.ArrayIndexOutOfBoundsException f){
						}
					}
				}
				if(play.y+play.height/2>ground.y&&play.x>=ground.x&&play.x<=ground.x+5&&ground.exists==true){
					if(play.y+play.height/2<ground.y+20){
						play.falling = false;
						play.y = ground.y - play.height/2;
						play.jumps = 0;
						play.platformNum = (int)(d+camx/5);
						if(Worldly.sObjects.get(play.platformNum+1).y+20<ground.y&&play.xVel>0){
							play.xVel = 0;
							play.x = ground.x;
						}else if(Worldly.sObjects.get(play.platformNum-1).y+20<ground.y&&play.xVel<0){
							play.xVel = 0;
							play.x = ground.x;
						}
					}else{
						if(play.xVel > 0){play.x = Worldly.sObjects.get((d+camx/5)-1).x;}
						if(play.xVel < 0){play.x = Worldly.sObjects.get((d+camx/5)+1).x+5;}
						play.xVel = 0;
					}
				}
			}
		}
		return Worldly;
	}
	
	
	/**public static boolean playerxGround(world World1, int camx, int camy, player Player){
		for(int e=0;e<=256;e++){
			sObject ground = World1.sObjects.get(e+camx/5);
			if(Player.y+Player.height/2+100>ground.y&&Player.x>ground.x&&Player.x<ground.x+5){
				System.out.println("Hit!");
				return true;
			}
		}
		return false;
	}**/
}
