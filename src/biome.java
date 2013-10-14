import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class biome {
	public Color bg;
	public Color bg1;
	public Color bg2;
	public int[] fg = new int[6];
	public int id;
	public int steepness = 0;
	public int gaps = 0;
	public int gapwidth = 0;
	public int comets = 0;
	List<sObject> bgObj = new ArrayList<sObject>();
	public biome(int ID){
		fg[0] = 0;
		fg[1] = 0;
		fg[2] = 0;
		fg[3] = 0;
		fg[4] = 0;
		fg[5] = 0;
		id = ID;
		switch(ID){
		case 0:
			bg = new Color(153,204,255);
			fg[2] = 51;
			fg[3] = 204;
			steepness = 3;
			comets = 8;
			for(int b = 0;b<=1;b++){
				this.bgObj.add(new sObject(1000 + (4000*b),0));
				this.bgObj.get(0+(b*10)).width=1000;
				this.bgObj.get(0+(b*10)).height=400;
				this.bgObj.get(0+(b*10)).color=new Color(0,90,0);

				this.bgObj.add(new sObject(1000 + (4000*b),0));
				this.bgObj.get(1+(b*10)).width=975;
				this.bgObj.get(1+(b*10)).height=375;
				this.bgObj.get(1+(b*10)).color=new Color(0,102,0);

				this.bgObj.add(new sObject(200 + (4000*b),100));
				this.bgObj.get(2+(b*10)).width=2000;
				this.bgObj.get(2+(b*10)).height=400;
				this.bgObj.get(2+(b*10)).color=new Color(0,90,0);

				this.bgObj.add(new sObject(200 + (4000*b),100));
				this.bgObj.get(3+(b*10)).width=1975;
				this.bgObj.get(3+(b*10)).height=375;
				this.bgObj.get(3+(b*10)).color=new Color(0,102,0);

				this.bgObj.add(new sObject(-200 + (4000*b),200));
				this.bgObj.get(4+(b*10)).width=2000;
				this.bgObj.get(4+(b*10)).height=400;
				this.bgObj.get(4+(b*10)).color=new Color(0,90,0);

				this.bgObj.add(new sObject(-200 + (4000*b),200));
				this.bgObj.get(5+(b*10)).width=1975;
				this.bgObj.get(5+(b*10)).height=375;
				this.bgObj.get(5+(b*10)).color=new Color(0,102,0);

				this.bgObj.add(new sObject(1200 + (4000*b),200));
				this.bgObj.get(6+(b*10)).width=2000;
				this.bgObj.get(6+(b*10)).height=600;
				this.bgObj.get(6+(b*10)).color=new Color(0,90,0);

				this.bgObj.add(new sObject(1200 + (4000*b),200));
				this.bgObj.get(7+(b*10)).width=1975;
				this.bgObj.get(7+(b*10)).height=575;
				this.bgObj.get(7+(b*10)).color=new Color(0,102,0);

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(8+(b*10)).width=2000;
				this.bgObj.get(8+(b*10)).height=600;
				this.bgObj.get(8+(b*10)).color=new Color(0,90,0);

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(9+(b*10)).width=1975;
				this.bgObj.get(9+(b*10)).height=575;
				this.bgObj.get(9+(b*10)).color=new Color(0,102,0);
			}
		break;
		case 1:
			bg = new Color(153,255,255);
			fg[0] = 102;
			fg[1] = 253;
			fg[2] = 255;
			fg[3] = 255;
			fg[4] = 255;
			fg[5] = 255;
			steepness = 0;
			gaps = 100;
			gapwidth = 5;
			comets = 9;
			for(int b = 0;b<=1;b++){
				int max = 6;
				this.bgObj.add(new sObject(0 + (4000*b),250));
				this.bgObj.get(0+(b*max)).width=2000;
				this.bgObj.get(0+(b*max)).height=400;
				this.bgObj.get(0+(b*max)).color=new Color(51,255,255);

				this.bgObj.add(new sObject(0 + (4000*b),250));
				this.bgObj.get(1+(b*max)).width=1975;
				this.bgObj.get(1+(b*max)).height=375;
				this.bgObj.get(1+(b*max)).color=new Color(0,204,204);
				
				this.bgObj.add(new sObject(500 + (4000*b),200));
				this.bgObj.get(2+(b*max)).width=2000;
				this.bgObj.get(2+(b*max)).height=400;
				this.bgObj.get(2+(b*max)).color=new Color(51,255,255);

				this.bgObj.add(new sObject(500 + (4000*b),200));
				this.bgObj.get(3+(b*max)).width=1975;
				this.bgObj.get(3+(b*max)).height=375;
				this.bgObj.get(3+(b*max)).color=new Color(0,204,204);

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(4+(b*max)).width=2500;
				this.bgObj.get(4+(b*max)).height=400;
				this.bgObj.get(4+(b*max)).color=new Color(51,255,255);

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(5+(b*max)).width=2475;
				this.bgObj.get(5+(b*max)).height=375;
				this.bgObj.get(5+(b*max)).color=new Color(0,204,204);
			}
		break;
		case 2:
			bg = new Color(102,255,255);
			fg[0] = 255;
			fg[1] = 255;
			fg[2] = 255;
			fg[3] = 255;
			fg[4] = 0;
			fg[5] = 100;
			steepness = 1;
			gaps = 200;
			gapwidth = 10;
			comets = 10;
			bg2 = new Color(204,204,0);
			bg1 = new Color(255,255,0);
			for(int b = 0;b<=1;b++){
				int max = 6;
				this.bgObj.add(new sObject(0 + (4000*b),250));
				this.bgObj.get(0+(b*max)).width=2000;
				this.bgObj.get(0+(b*max)).height=400;
				this.bgObj.get(0+(b*max)).color=bg1;

				this.bgObj.add(new sObject(0 + (4000*b),250));
				this.bgObj.get(1+(b*max)).width=1975;
				this.bgObj.get(1+(b*max)).height=375;
				this.bgObj.get(1+(b*max)).color=bg2;
				
				this.bgObj.add(new sObject(500 + (4000*b),200));
				this.bgObj.get(2+(b*max)).width=2000;
				this.bgObj.get(2+(b*max)).height=400;
				this.bgObj.get(2+(b*max)).color=bg1;

				this.bgObj.add(new sObject(500 + (4000*b),200));
				this.bgObj.get(3+(b*max)).width=1975;
				this.bgObj.get(3+(b*max)).height=375;
				this.bgObj.get(3+(b*max)).color=bg2;

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(4+(b*max)).width=2500;
				this.bgObj.get(4+(b*max)).height=400;
				this.bgObj.get(4+(b*max)).color=bg1;

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(5+(b*max)).width=2475;
				this.bgObj.get(5+(b*max)).height=375;
				this.bgObj.get(5+(b*max)).color=bg2;
			}
		break;
		case 3:
			bg = new Color(255,204,204);
			fg[0] = 102;
			fg[1] = 102;
			fg[2] = 0;
			fg[3] = 51;
			steepness = 5;
			gaps = 300;
			gapwidth = 15;
			comets = 11;
			bg1 = new Color(102,51,0);
			bg2 = new Color(153,76,0);
			for(int b = 0;b<=1;b++){
				this.bgObj.add(new sObject((4000*b),0));
				this.bgObj.get(0+(b*10)).width=1000;
				this.bgObj.get(0+(b*10)).height=800;
				this.bgObj.get(0+(b*10)).color=bg1;

				this.bgObj.add(new sObject((4000*b),0));
				this.bgObj.get(1+(b*10)).width=975;
				this.bgObj.get(1+(b*10)).height=775;
				this.bgObj.get(1+(b*10)).color=bg2;

				this.bgObj.add(new sObject(600 + (4000*b),0));
				this.bgObj.get(2+(b*10)).width=800;
				this.bgObj.get(2+(b*10)).height=1000;
				this.bgObj.get(2+(b*10)).color=bg1;

				this.bgObj.add(new sObject(600 + (4000*b),0));
				this.bgObj.get(3+(b*10)).width=775;
				this.bgObj.get(3+(b*10)).height=975;
				this.bgObj.get(3+(b*10)).color=bg2;

				this.bgObj.add(new sObject(-200 + (4000*b),350));
				this.bgObj.get(4+(b*10)).width=2000;
				this.bgObj.get(4+(b*10)).height=800;
				this.bgObj.get(4+(b*10)).color=bg1;

				this.bgObj.add(new sObject(-200 + (4000*b),350));
				this.bgObj.get(5+(b*10)).width=1975;
				this.bgObj.get(5+(b*10)).height=775;
				this.bgObj.get(5+(b*10)).color=bg2;

				this.bgObj.add(new sObject(1200 + (4000*b),200));
				this.bgObj.get(6+(b*10)).width=2000;
				this.bgObj.get(6+(b*10)).height=600;
				this.bgObj.get(6+(b*10)).color=bg1;

				this.bgObj.add(new sObject(1200 + (4000*b),200));
				this.bgObj.get(7+(b*10)).width=1975;
				this.bgObj.get(7+(b*10)).height=575;
				this.bgObj.get(7+(b*10)).color=bg2;

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(8+(b*10)).width=1500;
				this.bgObj.get(8+(b*10)).height=2000;
				this.bgObj.get(8+(b*10)).color=bg1;

				this.bgObj.add(new sObject(2500 + (4000*b),150));
				this.bgObj.get(9+(b*10)).width=1475;
				this.bgObj.get(9+(b*10)).height=1975;
				this.bgObj.get(9+(b*10)).color=bg2;
			}
		break;
		}
	}
}
