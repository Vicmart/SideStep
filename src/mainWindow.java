
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Formatter;

import javax.swing.*;

public class mainWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int boxWidth = 1280;
	private static final int boxHeight = 600;

	private static world World = new world();
	private static int maxC = 16;
	private static int maxT = 262144;
	private static camera cam = new camera();

	//public static boolean[] keys = new boolean[1000]; 

	private static final int update = 60*maxC; 

	public static void main(String[] args) {
		World.boxheight = boxHeight;
		World.boxwidth = boxWidth;
		for(int i=0;i<maxC;i++){
			World.addObject(new dObject(-500,-50,random(-5,10),random(5,10),1.0));
			World.dObjects.get(i).width = random(50,80);
			World.dObjects.get(i).color = new Color(random(51,102), random(0,51), 0);
		}
		for(int b=0;b<=(int)(maxT/World.biomeSize);b++){
			World.addBiome(new biome(0));
			World.addBiome(new biome(1));
			World.addBiome(new biome(2));
			World.addBiome(new biome(3));
		}
		int[] bio = World.Biomes.get(0).fg;
		World.addSun(new sun(boxWidth,100));
		
		for(int i=0;i<=maxT;i++){
			World.addObject(new sObject(i*5,300));
			World.sObjects.get(i).color = new Color(0,random(51,204),0);
			//System.out.println(i);
		}
		World.addPlayer(new player(100,100,0,0));


		World = terrainAlgorithm.main(World);
		World.currentBiome=0;
		
		World.addNotification(new notification("Use the arrow keys to move",1));
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("SideStep V1.01");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new mainWindow());
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(false);
				frame.addKeyListener(new KeyListener()
				{
					public void keyPressed(KeyEvent e)
					{
						player player = World.Players.get(0);
						if((int)e.getKeyCode() == 39) {
							World.keys[37] = false;
							if(World.tutorials==1){
								World.tutorials++;
								World.addNotification(new notification("Press [Space Bar] to jump",1));
							}
						}
						if((int)e.getKeyCode() == 37) {
							World.keys[39] = false;
							if(World.tutorials==1){
								World.tutorials++;
								World.addNotification(new notification("Press [Space Bar] to jump",1));
								}
						}
						if((int)e.getKeyCode() == 17) {
							if(World.keys[17]==false){player.addRocket(player.x, player.y - 55);}
							if(World.tutorials==5){
								World.tutorials++;
								World.addNotification(new notification("Hold [Shift] to repair hull",1));
							}
						}
						if((int)e.getKeyCode() == 32) {
							if(World.tutorials==3){
								World.tutorials++;
								World.addNotification(new notification("Hold [Ctrl] to fire rockets",1));
							}
						}
						if((int)e.getKeyCode() == 16) {
							if(World.tutorials==7){
								World.tutorials++;
								World.addNotification(new notification("Press [Enter] to pause",1));
							}
						}
						if((int)e.getKeyCode()==10){
							if(World.pause==false){
								World.pause=true;
								if(World.tutorials==9){
									World.tutorials++;
									World.addNotification(new notification("Energy drains faster when",3));
									World.addNotification(new notification("repairing hull and ",2));
									World.addNotification(new notification("firing rockets",1));
								}
							}else{
								World.pause=false;
							}
						}
						World.keys[(int)e.getKeyCode()]=true;
						//System.out.println(e.getKeyCode());
						cam.stopAtZero=false; 
					}
					public void keyReleased(KeyEvent e) {
						World.keys[(int)e.getKeyCode()]=false;
						//cam.stopAtZero=true;
					}
					public void keyTyped(KeyEvent e) {
					} 
				});

			}
		});
	}


	public static int random(int Min, int Max){
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}

	public static dObject resetComet(dObject obj){
		obj.yVel = random(3,7);
		obj.xVel = random(3,9);
		obj.x = random(200, boxWidth - 50 + (int)cam.x);
		obj.y = -100 + cam.y;
		obj.width = random(50,80);
		obj.color = new Color(random(51,102), random(0,51), 0);
		obj.rocket = false;
		return obj;
	}

	public static void gameOver(){
		World.Players.get(0).finalScore = (World.currentBiome+1) * (5000-((World.biomeSize*(World.currentBiome+1))*5 - cam.x)/10);
		World.gameOver = true;
	}

	public static void revive(){
		if(World.Players.get(0).lives>0){
			World.Players.get(0).yVel = -2;
			World.Players.get(0).y -= 100;
			World.Players.get(0).yAcel = 0;
			restart();
		}else{
			gameOver();
		}

	}

	public static void restart(){
		World.Players.get(0).lives--;
		if(World.Players.get(0).lives==-1){gameOver();}
		if(World.Players.get(0).lives!=1){
			World.addNotification(new notification(World.Players.get(0).lives + " lives left", 0));
		}else{
			World.addNotification(new notification(World.Players.get(0).lives + " life left", 0));
		}
	}

	public mainWindow() {
		this.setPreferredSize(new Dimension(boxWidth, boxHeight));

		final Thread cometThread = new Thread() {
			public void run() {
				while (true) { 
					if(World.pause==false){
						for(int i=0;i<World.Biomes.get(World.currentBiome).comets;i++){
							dObject temp = World.dObjects.get(i);
							player p1 = World.Players.get(0);
							temp.yVel += 0.098;
							temp.x += temp.xVel;
							temp.y += temp.yVel;
							if (temp.x - (temp.width)/2.0 < -100 + cam.x||temp.x + (temp.width)/2.0 > boxWidth + 100 + cam.x||temp.y + (temp.width)/2.0 > boxHeight + 500 + cam.y) {
								temp = resetComet(temp);
							}
							if(temp.exploding==true){
								temp.color= new Color(153,0,0);
								temp.xVel=0;
								temp.yVel=0;
								temp.width+=2;
								temp.transparency-=0.03;
								if(temp.rocket==true){p1.energy+=temp.width/500;p1.engIncrease+=temp.width/500;p1.engITimer = 1000;}
							}
							if(temp.transparency<=0.0){
								temp.transparency=1.0;
								temp.exploding=false;
								temp = resetComet(temp);
							}
							if(Math.sqrt(Math.pow(p1.x-temp.x,2)+Math.pow(p1.y-temp.y, 2))<temp.width){
								temp.exploding = true;
								p1.health -= (temp.transparency * temp.width)/500;
								p1.damages -= (temp.transparency * temp.width)/500;
								p1.damageTimer = 1000;
							}

							int lim = World.Players.get(0).Rockets.size();
							for(int r=0;r<lim;r++){
								try{
									if(World.Players.get(0).Rockets.get(r) != null){
										if(Math.sqrt(Math.pow(World.Players.get(0).Rockets.get(r).x-temp.x,2)+Math.pow(World.Players.get(0).Rockets.get(r).y-temp.y, 2))<temp.width/2 && temp.exploding==false){
											temp.exploding=true;
											temp.rocket=true;
											World.Players.get(0).Rockets.remove(r);
										}
									}

								} catch(java.lang.IndexOutOfBoundsException e){}	 
							}
							repaint();
						}
					}
					try {
						Thread.sleep(1000/60);  
					} catch (InterruptedException ex) { }
				}
			}
		};
		cometThread.start(); 

		Thread waitThread = new Thread() {
			public void run() {
				while (true) { 
					if(World.keys[17]==true){
						World.Players.get(0).addRocket(World.Players.get(0).x , World.Players.get(0).y - 55); 
					}
					try {
						Thread.sleep(250);  
					} catch (InterruptedException ex) { }
				}
			}
		};
		waitThread.start();  
		
		Thread checkThread = new Thread() {
			public void run(){
				while (true){
					World = collisionDetection.ballxGround(World, (int)cam.x, (int) cam.y);	
					if(World.pause==false){if(World.gameOver==false){World.time+=0.0001;}
					try{
						for(int x=0;x<World.Notifications.size();x++){
							if(World.Notifications.get(x).waiting==-1){
								World.Notifications.get(x).xVel += World.Notifications.get(x).xAcel;
								World.Notifications.get(x).x += World.Notifications.get(x).xVel;
								if(World.Notifications.get(x).x > 100){if(World.tutorials%2==0){World.tutorials++;}World.Notifications.remove(x);}
								if(Math.abs(World.Notifications.get(x).xVel) < 0.001){World.Notifications.get(x).waiting=0.0;}
							}else{
								World.Notifications.get(x).waiting+=1;
								if(World.Notifications.get(x).waiting>10){
									World.Notifications.get(x).waiting=-1;
								}
							}
						}
					}catch(java.lang.IndexOutOfBoundsException error){}}
				}
			}
		};
		checkThread.start();

		final Thread playerThread = new Thread() {
			public void run() {
				while (true) { 
					if(World.pause==false){
						player pl = World.Players.get(0);
						if(pl.yAcel==0&&pl.y<500+cam.y){
							pl.yAcel = 0.0049;
						}

						if(World.keys[16]==true&&pl.energy>0&&pl.health<100){
							if(World.keys[17]==false){
								pl.health+=0.01;pl.heal+=0.01;pl.healTimer=1000;pl.energy -= 0.1;pl.engDecrease += 0.1;pl.engDTimer = 1000;
							}else{
								pl.health+=0.001;pl.heal+=0.001;pl.healTimer=1000;pl.energy -= 0.05;pl.engDecrease += 0.05;pl.engDTimer = 1000;
							}
						}

						if(pl.health<=0){
							pl.health=0;
							pl.degrees=60;
							pl.xVel=0;
							World.keys[32]=false;
							if(World.lastChance==-1&&World.gameOver==false){if(World.Players.get(0).lives>1){World.lastChance=0.0;}else{gameOver();}}
						}else if(World.lastChance!=-1){
							World.lastChance=-1;
							restart();
						}

						if(pl.health>100){pl.health=100;}
						if(pl.energy<0){pl.energy=0;}
						if(World.keys[32]==true&&pl.jumps<2){
							pl.yVel=-1.4;
							pl.jumps++;
						}

						pl.yVel += pl.yAcel;

						if(World.keys[39]==true&&pl.x - cam.x<boxWidth - 200){if(pl.xVel<pl.xVelLimit){pl.xVel+=0.15;}}else{pl.xVel=pl.xVel - pl.xVel/64;}
						if(World.keys[39]==true){pl.flipped=false;}
						if(World.keys[37]==true&&pl.x - cam.x>200){if(pl.xVel>-pl.xVelLimit){pl.xVel-=0.15;}}else{pl.xVel=pl.xVel - pl.xVel/64;}
						if(World.keys[37]==true){pl.flipped=true;}
						pl.degrees = pl.xVel*20;


						if(pl.y>1000+cam.y){
							revive();
						}

						if(pl.falling){
							pl.y += pl.yVel;
						}else{
							pl.yVel = 0;
						}

						pl.x += pl.xVel;
						pl.falling=true;
						pl.refresh();
						repaint(); 
						int lim = World.Players.get(0).Rockets.size();
						for(int r=0;r<lim;r++){
							try{
								if(World.Players.get(0).Rockets.get(r) != null){
									rocket rock = World.Players.get(0).Rockets.get(r);
									rock.yVel-=0.005;
									rock.y+=rock.yVel;
									if(rock.target != null && rock.target.y>0){
										rock.xVel = (rock.target.x - rock.x)/(rock.y - rock.target.y);
									}else{
										rock.target(World);
									}
									rock.x += rock.xVel;
									if(rock.y - cam.y < -30){
										World.Players.get(0).Rockets.remove(r);
									}
								}

							} catch(java.lang.IndexOutOfBoundsException e){}	 
						}
					}
					try {
						Thread.sleep(1000/update);  
					} catch (InterruptedException ex) { }
				}
			}
		};
		playerThread.start();

		Thread sunThread = new Thread() {
			public void run() {
				while (true) { 
					sun temp = World.Suns.get(0);
					temp.x += ((boxWidth/2)-temp.x)/1000;
					temp.y += temp.yVel;
					try {
						Thread.sleep(1000/update);  
					} catch (InterruptedException ex) { }

				}
			}
		};
		sunThread.start();

		Thread camThread = new Thread() {
			public void run() {
				while (true) {  
					if(World.pause==false){
						player player = World.Players.get(0);
						if(World.sObjects.get(player.platformNum).unstable==false){	
							cam.y += ((World.sObjects.get(player.platformNum).y-500)-cam.y)/512;
						}
						if(player.x - cam.x > boxWidth - 400){cam.xAcel=0.01;player.xVel=cam.xVel;}
						if(player.x - cam.x < 200){cam.xAcel=-0.01;player.xVel=cam.xVel;}
						//if(World.keys[39]==false||World.keys[37]==false){cam.xAcel=0;}
						cam.xVel+=cam.xAcel;
						cam.yVel+=cam.yAcel;
						if(cam.xVel>cam.limit){cam.xVel=cam.limit;}
						if(cam.xVel<-cam.limit){cam.xVel=-cam.limit;}

						if(cam.x + cam.xVel<World.biomeSize*5*(World.currentBiome)){
							cam.xVel=0;
						}

						cam.x+=cam.xVel;
						cam.y+=cam.yVel;
						if(World.keys[37]==false&&player.x - cam.x<boxWidth - 450){
							cam.xVel=0;
							cam.xAcel=0;
							cam.stopAtZero=false;
						}else if(World.keys[39]==false&&player.x - cam.x>250){
							cam.xVel=0;
							cam.xAcel=0;
							cam.stopAtZero=false;
						}
						if(cam.x<0){
							cam.x=0;
							cam.xVel=0;
						}
					}
					try {
						Thread.sleep(1000/update);  
					} catch (InterruptedException ex) { }
				}
			}
		};
		camThread.start();
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   

		player pl = World.Players.get(0);

		if(cam.x>World.biomeSize*5*(World.currentBiome+1)){
			World.currentBiome++;
			World.biomeStart = World.biomeSize*(World.currentBiome)+1700;
			World.addNotification(new notification("Zone " + new DecimalFormat("#00").format(World.currentBiome+1), -1));
		}

		World.bg[0] += (World.Biomes.get(World.currentBiome).bg.getRed() - World.bg[0])/32;
		World.bg[1] += (World.Biomes.get(World.currentBiome).bg.getGreen() - World.bg[1])/32;
		World.bg[2] += (World.Biomes.get(World.currentBiome).bg.getBlue() - World.bg[2])/32;
		
		g.setColor(new Color(World.bg[0], World.bg[1], World.bg[2]));
		g.fillRect(0, 0, boxWidth + 10, boxHeight);
		
		/**for(int s=1;s<=5;s++){
			g.setColor(new Color(World.Suns.get(0).sunC.getRed()/255,World.Suns.get(0).sunC.getGreen()/255,World.Suns.get(0).sunC.getBlue()/255,(float)s/5));
			g.fillOval((int)World.Suns.get(0).x + s*2, (int)World.Suns.get(0).y + s*2, (int)World.Suns.get(0).width - s*4, (int)World.Suns.get(0).width - s*4);
		}**/
		
		for(int pat=0;pat<World.Biomes.get(World.currentBiome).bgObj.size();pat++){
			sObject Phuong_Anh = World.Biomes.get(World.currentBiome).bgObj.get(pat);
			g.setColor(Phuong_Anh.color);
			g.fillOval((int)(World.biomeStart + boxWidth/2 + Phuong_Anh.x - Phuong_Anh.width/2 - cam.x/5), (int)(boxHeight/2 + Phuong_Anh.y - Phuong_Anh.height/4 - cam.y/10), (int)(Phuong_Anh.width), (int)(Phuong_Anh.height));
		}

		for(int r=0;r<World.Players.get(0).Rockets.size();r++){
			rocket rock = World.Players.get(0).Rockets.get(r);
			g.setColor(rock.color);
			g.fillOval((int)(rock.x - cam.x), (int)(rock.y - cam.y), (int)rock.width, (int)rock.height);
		}

		int[] xpoint = new int[4];
		int[] ypoint = new int[4];

		xpoint = transformX(pl.legf.xpoints, pl.legf.ypoints, pl.x, pl.y, pl.degrees*2);
		ypoint = transformY(pl.legf.xpoints, pl.legf.ypoints, pl.x, pl.y, pl.degrees*2);

		g.setColor(new Color(0,0,102));
		g.fillPolygon(xpoint,ypoint,4);

		xpoint = transformX(pl.neck.xpoints, pl.neck.ypoints, pl.x, pl.y, pl.degrees);
		ypoint = transformY(pl.neck.xpoints, pl.neck.ypoints, pl.x, pl.y, pl.degrees);

		g.setColor(Color.GRAY);
		g.fillPolygon(xpoint,ypoint,4);

		xpoint = transformX(pl.body.xpoints, pl.body.ypoints, pl.x, pl.y, pl.degrees);
		ypoint = transformY(pl.body.xpoints, pl.body.ypoints, pl.x, pl.y, pl.degrees);

		g.setColor(Color.BLUE);
		g.fillPolygon(xpoint,ypoint,4);

		xpoint = transformX(pl.foot.xpoints, pl.foot.ypoints, pl.x, pl.y, pl.degrees*2);
		ypoint = transformY(pl.foot.xpoints, pl.foot.ypoints, pl.x, pl.y, pl.degrees*2);

		g.setColor(Color.GRAY);
		g.fillPolygon(xpoint,ypoint,4);

		xpoint = transformX(pl.armf.xpoints, pl.armf.ypoints, pl.x, pl.y, pl.degrees*1.5);
		ypoint = transformY(pl.armf.xpoints, pl.armf.ypoints, pl.x, pl.y, pl.degrees*1.5);

		g.setColor(new Color(0,0,153));
		g.fillPolygon(xpoint,ypoint,4);

		xpoint = transformX(pl.hand.xpoints, pl.hand.ypoints, pl.x, pl.y, pl.degrees*1.5);
		ypoint = transformY(pl.hand.xpoints, pl.hand.ypoints, pl.x, pl.y, pl.degrees*1.5);

		g.setColor(Color.DARK_GRAY);
		g.fillPolygon(xpoint,ypoint,4);

		xpoint = transformX(pl.head.xpoints, pl.head.ypoints, pl.x, pl.y, pl.degrees);
		ypoint = transformY(pl.head.xpoints, pl.head.ypoints, pl.x, pl.y, pl.degrees);

		g.setColor(Color.BLUE);
		g.fillPolygon(xpoint,ypoint,4);

		for(int i=0;i<World.dObjects.size();i++){
			dObject temp = World.dObjects.get(i);
			if(temp.exploding==false){
				for(int t=1;t<=5;t++){
					g.setColor(new Color((float)1,(float)0,(float)0,(float)0.5));
					g.fillOval((int) ((temp.x - temp.xVel * t) - (temp.width)/2 - cam.x), (int) ((temp.y - temp.yVel * t) - (temp.width)/2 - cam.y), (int)(temp.width*2)/(t+1), (int)(temp.width*2)/(t+1));
				}
			}
			try{
				g.setColor(new Color((float)temp.color.getRed()/255,(float)temp.color.getGreen()/255,(float)temp.color.getBlue()/255,(float)temp.transparency));
				g.fillOval((int) (temp.x - (temp.width)/2 - cam.x), (int) (temp.y - (temp.width)/2 - cam.y), (int)temp.width, (int)temp.width);
			}catch(java.lang.IllegalArgumentException error){}  

		}

		for(int i=(int)cam.x/5;i<(int)cam.x/5+1+(boxWidth+10)/5;i++){
			sObject temp = World.sObjects.get(i);
			g.setColor(temp.color);
			if(temp.exists==true){
				g.fillRect((int)(temp.x - cam.x), (int)(temp.y - cam.y), (int) temp.width, (int) temp.height);
			}
			if(World.Biomes.get(World.currentBiome).id==2){
				g.setColor(new Color((float)0.2,(float)0.6,(float)1.0,(float)0.6));
				if(((World.biomeSize*(World.currentBiome+1))*5 - cam.x)/10>500){
					g.fillRect((int)(temp.x - cam.x), (int)((20 * Math.sin((World.time * 50 + i)/100)+50) - cam.y * 1.1) + (int)(Math.abs(((World.biomeSize*(World.currentBiome+1))*5 - cam.x)/10)-World.biomeSize/8)/10 , (int) temp.width, (int) temp.height);
				}else{
					g.fillRect((int)(temp.x - cam.x), (int)((20 * Math.sin((World.time * 50 + i)/100)+50) - cam.y * 1.1) + (int)(Math.abs(((World.biomeSize*(World.currentBiome+1))*5 - cam.x)/10)-World.biomeSize/8)/10 - (int)(Math.abs(((World.biomeSize*(World.currentBiome+1))*5 - cam.x)/10)-500) , (int) temp.width, (int) temp.height);
				}
			}
		}
		
		g.setColor(new Color(160,160,160));
		g.fillRoundRect(-20, 9, 305, 40, 20, 50);
		g.fillRoundRect(-20, 59, 230, 40, 20, 50);
		g.fillRoundRect(-20, boxHeight - 52, 415, 40, 20, 50);
		g.fillRoundRect(boxWidth - 260, 9, 350, 40, 20, 50);
		try{
			for(int n=0;n<World.Notifications.size();n++){
				g.setColor(new Color(160,160,160));
				if(World.Notifications.get(n).id==-1){
					g.fillRoundRect(-(int)World.Notifications.get(n).x - 340, boxHeight-102, 350, 40, 20, 50);
				}else{
					if(World.Notifications.get(n).id==3){
						g.fillRoundRect(boxWidth + 10 + (int)World.Notifications.get(n).x, boxHeight-52 - (50*World.Notifications.get(n).id), 700, 120, 20, 50);
					}else{
						g.fillRoundRect(boxWidth + 10 + (int)World.Notifications.get(n).x, boxHeight-52 - (50*World.Notifications.get(n).id), 700, 40, 20, 50);
					}
				}
				g.setColor(Color.WHITE);
				g.setFont(new Font("Century Gothic", Font.PLAIN, 36));
				if(World.Notifications.get(n).id==-1){
					g.drawString(World.Notifications.get(n).message, -(int)World.Notifications.get(n).x - 135, boxHeight- 70 );
				}else{
					g.drawString(World.Notifications.get(n).message, boxWidth + 10 + (int)World.Notifications.get(n).x + 10, boxHeight-20 - (50*World.Notifications.get(n).id));
				}
			}
		} catch(java.lang.IndexOutOfBoundsException e){}	 

		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 36));
		DecimalFormat health = new DecimalFormat("#000.00");
		DecimalFormat Eng = new DecimalFormat("#0000.00");
		g.drawString("Hull " + health.format(pl.health), 20, 90);
		g.drawString("Energy " + Eng.format(Math.abs(pl.energy)), 20, 40);


		DecimalFormat damage = new DecimalFormat("-#00.00");
		float alpha;
		if((100-(pl.damageTimer/20))<=75){alpha = (float)(1-(Math.abs((100-(pl.damageTimer/20)) - 75))/25);}else{alpha = (float)(1-(Math.abs((100-(pl.damageTimer/20)) - 75))/50);}
		g.setColor(new Color((float)0.6,(float)0.0,(float)0.0,alpha));
		pl.damageTimer -= 10;
		if(pl.damageTimer>0){g.drawString("" + damage.format(Math.abs(pl.damages)),101,(int)(80-(pl.damageTimer/40)) + 40);}else{pl.damages=0;pl.damageTimer=0;}

		damage = new DecimalFormat("+#00.00");
		if((100-(pl.healTimer/20))<=75){alpha = (float)(1-(Math.abs((100-(pl.healTimer/20)) - 75))/25);}else{alpha = (float)(1-(Math.abs((100-(pl.healTimer/20)) - 75))/50);}
		g.setColor(new Color((float)0.0,(float)0.6,(float)0.0,alpha));
		pl.healTimer -= 10;
		if(pl.healTimer>0){g.drawString("" + damage.format(Math.abs(pl.heal)),(int)(80-(pl.healTimer/40)) + 130, 92);}else{pl.heal=0;pl.healTimer=0;}

		DecimalFormat eng = new DecimalFormat("+#00.00");
		if((100-(pl.engITimer/20))<=75){alpha = (float)(1-(Math.abs((100-(pl.engITimer/20)) - 75))/25);}else{alpha = (float)(1-(Math.abs((100-(pl.engITimer/20)) - 75))/50);}
		g.setColor(new Color((float)0.0,(float)0.6,(float)0.0,alpha));
		pl.engITimer -= 10;
		if(pl.engITimer>0){g.drawString("" + eng.format(Math.abs(pl.engIncrease)),(int)(80-(pl.engITimer/40)) + 200,42);}else{pl.engIncrease=0;pl.engITimer=0;}

		eng = new DecimalFormat(" -#00.00");
		if((100-(pl.engDTimer/20))<=75){alpha = (float)(1-(Math.abs((100-(pl.engDTimer/20)) - 75))/25);}else{alpha = (float)(1-(Math.abs((100-(pl.engDTimer/20)) - 75))/50);}
		g.setColor(new Color((float)0.6,(float)0.0,(float)0.0,alpha));
		pl.engDTimer -= 10;
		if(pl.engDTimer>0){g.drawString("" + eng.format(Math.abs(pl.engDecrease)),165,(int)(80-(pl.engDTimer/40)));}else{pl.engDecrease=0;pl.engDTimer=0;}

		g.setColor(Color.WHITE);
		DecimalFormat length = new DecimalFormat("#0000.0");
		g.drawString("Next Zone in " + length.format(((World.biomeSize*(World.currentBiome+1))*5 - cam.x)/10) + "m", 20, boxHeight-20);

		DecimalFormat sec= new DecimalFormat("#00.0s");  
		DecimalFormat min= new DecimalFormat("#00m");
		DecimalFormat hour= new DecimalFormat("#00h");
		g.drawString("" + hour.format((((World.time - (World.time%60))/60) - ((World.time - (World.time%60))/60)%60)/60) + min.format(((World.time - (World.time%60))/60)%60) + sec.format(World.time%60), boxWidth-250, 40);

		if(World.gameOver==true){
			g.setColor(new Color((float)0.5,(float)0.5,(float)0.5,(float)1.0));
			g.fillRect(0, 0, boxWidth + 10, boxHeight + 10);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 72));
			g.drawString("Game Over", boxWidth/2 - 225, boxHeight/2);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 40));
			g.drawString("Final Score " + new DecimalFormat("#0000000").format(World.Players.get(0).finalScore), boxWidth/2 - 200, boxHeight/2 + 40);
		}
		
		if(World.pause==true&&World.gameOver==false){
			g.setColor(new Color((float)0.5,(float)0.5,(float)0.5,(float)0.5));
			g.fillRect(0, 0, boxWidth + 10, boxHeight + 10);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 72));
			g.drawString("Game Paused", boxWidth/2 - 225, boxHeight/2);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 40));
			g.drawString("Press [Enter] to resume", boxWidth/2 - 200, boxHeight/2 + 40);
		}

		if(World.lastChance>-1){
			World.lastChance+=0.001;
			g.setColor(new Color((float)0.5,(float)0.5,(float)0.5,(float)World.lastChance));
			g.fillRect(0, 0, boxWidth + 10, boxHeight + 10);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 72));
			g.drawString("Last chance", boxWidth/2 - 225, boxHeight/2);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
			g.drawString("Regain enough energy to repair the hull", boxWidth/2 - 300, boxHeight/2 + 40);
			if(World.lastChance>=1){
				World.lastChance=-1;
				gameOver();
			}
		}

	}
	public static int[] transformX(int[] x, int[] y, double px, double py, double degree){
		int[] p = new int[4];
		p[0] = (int)(((x[0]-px) * Math.cos(Math.toRadians(degree)) - (y[0]-py) * Math.sin(Math.toRadians(degree)))+px)-(int)cam.x;
		p[1] = (int)(((x[1]-px) * Math.cos(Math.toRadians(degree)) - (y[1]-py) * Math.sin(Math.toRadians(degree)))+px)-(int)cam.x;
		p[2] = (int)(((x[2]-px) * Math.cos(Math.toRadians(degree)) - (y[2]-py) * Math.sin(Math.toRadians(degree)))+px)-(int)cam.x;
		p[3] = (int)(((x[3]-px) * Math.cos(Math.toRadians(degree)) - (y[3]-py) * Math.sin(Math.toRadians(degree)))+px)-(int)cam.x;

		return p;
	}

	public static int[] transformY(int[] x, int[] y, double px, double py, double degree){
		int[] pt = new int[4];

		pt[0] = (int)(((x[0]-px) * Math.sin(Math.toRadians(degree)) + (y[0]-py) * Math.cos(Math.toRadians(degree)))+py)-(int)cam.y;
		pt[1] = (int)(((x[1]-px) * Math.sin(Math.toRadians(degree)) + (y[1]-py) * Math.cos(Math.toRadians(degree)))+py)-(int)cam.y;
		pt[2] = (int)(((x[2]-px) * Math.sin(Math.toRadians(degree)) + (y[2]-py) * Math.cos(Math.toRadians(degree)))+py)-(int)cam.y;
		pt[3] = (int)(((x[3]-px) * Math.sin(Math.toRadians(degree)) + (y[3]-py) * Math.cos(Math.toRadians(degree)))+py)-(int)cam.y;

		return pt;
	}

}