import java.awt.Polygon;
import java.util.*;


public class player {
	public double x = 0;
	public double y = 0;
	public double xVel = 0;
	public double yVel = 0;
	public double xAcel = 0;
	public double yAcel = 0.0049;
	public double xVelLimit = 0.5;
	public double width = 80.0;
	public double height = 110.0;
	public double degrees = 0.0;
	public double health = 100.0;
	public double damages = 0.0;
	public double damageTimer = 0.0;
	public double heal = 0.0;
	public double healTimer = 0.0;
	public double energy = 0.0;
	public double engIncrease = 0.0;
	public double engDecrease = 0.0;
	public double engITimer = 0.0;
	public double engDTimer = 0.0;
	public boolean falling = true;
	public double finalScore = 0.0;
	public int lives = 5;
	public int jumps = 0;
	public int platformNum = 100;
	public boolean flipped = false;
	List<rocket> Rockets = new ArrayList<rocket>();
	public Polygon body = new Polygon();
	public Polygon armf = new Polygon();
	public Polygon hand = new Polygon();
	public Polygon legf = new Polygon();
	public Polygon foot = new Polygon();
	public Polygon head = new Polygon();
	public Polygon neck = new Polygon();
	public player(double a, double b, double c, double d){
		x = a;
		y = b;
		xVel = c;
		yVel = d;
		this.refresh();
	}	
	public void addRocket(double x, double y){
		Rockets.add(new rocket(x,y));
	}
	public void refresh(){
		player p = this;
		p.body.reset();
		p.body.addPoint((int) (p.x-p.width/4) , (int) (p.y-p.height/4));
		p.body.addPoint((int) (p.x+p.width/4), (int) (p.y-p.height/4));
		p.body.addPoint((int) (p.x+p.width/4), (int) (p.y+p.height/4));
		p.body.addPoint((int) (p.x-p.width/4), (int) (p.y+p.height/4));
		p.body.npoints = 4;
		
		p.armf.reset();
		p.armf.addPoint((int) (p.x - p.width/8), (int) (p.y - p.height/8));
		p.armf.addPoint((int) (p.x + p.width/8), (int) (p.y - p.height/8));
		p.armf.addPoint((int) (p.x + p.width/8), (int) (p.y + p.height/5));
		p.armf.addPoint((int) (p.x - p.width/8), (int) (p.y + p.height/5));
		p.armf.npoints = 4;
		
		p.hand.reset();
		p.hand.addPoint((int) (p.x - p.width/7), (int) (p.y + (p.height/6)));
		p.hand.addPoint((int) (p.x + p.width/7), (int) (p.y + (p.height/6)));
		p.hand.addPoint((int) (p.x + p.width/7), (int) (p.y + (p.height/4)));
		p.hand.addPoint((int) (p.x - p.width/7), (int) (p.y + (p.height/4)));
		p.hand.npoints = 4;
		
		p.legf.reset();
		p.legf.addPoint((int) (p.x - p.width/8), (int) (p.y + (p.height/6)));
		p.legf.addPoint((int) (p.x + p.width/8), (int) (p.y + (p.height/6)));
		p.legf.addPoint((int) (p.x + p.width/8), (int) (p.y + (p.height/2)));
		p.legf.addPoint((int) (p.x - p.width/8), (int) (p.y + (p.height/2)));
		p.legf.npoints = 4;
		

		p.foot.reset();
		if(p.flipped==false){
		p.foot.addPoint((int) (p.x - p.width/6), (int) (p.y + (p.height/2.5)));
		p.foot.addPoint((int) (p.x + p.width/5), (int) (p.y + (p.height/2.5)));
		p.foot.addPoint((int) (p.x + p.width/5), (int) (p.y + (p.height/2)));
		p.foot.addPoint((int) (p.x - p.width/6), (int) (p.y + (p.height/2)));
		}else{
		p.foot.addPoint((int) (p.x - p.width/5), (int) (p.y + (p.height/2.5)));
		p.foot.addPoint((int) (p.x + p.width/6), (int) (p.y + (p.height/2.5)));
		p.foot.addPoint((int) (p.x + p.width/6), (int) (p.y + (p.height/2)));
		p.foot.addPoint((int) (p.x - p.width/5), (int) (p.y + (p.height/2)));
		}
		p.foot.npoints = 4;
		
		p.head.reset();
		if(p.flipped==false){
		p.head.addPoint((int) (p.x - p.width/6), (int) (p.y - (p.height/3.5)));
		p.head.addPoint((int) (p.x + p.width/5), (int) (p.y - (p.height/3.5)));
		p.head.addPoint((int) (p.x + p.width/5), (int) (p.y - (p.height/2)));
		p.head.addPoint((int) (p.x - p.width/6), (int) (p.y - (p.height/2)));
		}else{
		p.head.addPoint((int) (p.x - p.width/5), (int) (p.y - (p.height/3.5)));
		p.head.addPoint((int) (p.x + p.width/6), (int) (p.y - (p.height/3.5)));
		p.head.addPoint((int) (p.x + p.width/6), (int) (p.y - (p.height/2)));
		p.head.addPoint((int) (p.x - p.width/5), (int) (p.y - (p.height/2)));		
		}
		p.head.npoints = 4;

		p.neck.reset();
		p.neck.addPoint((int) (p.x - p.width/8), (int) (p.y - (p.height/4)));
		p.neck.addPoint((int) (p.x + p.width/8), (int) (p.y - (p.height/4)));
		p.neck.addPoint((int) (p.x + p.width/8), (int) (p.y - (p.height/3)));
		p.neck.addPoint((int) (p.x - p.width/8), (int) (p.y - (p.height/3)));
		p.neck.npoints = 4;
	}
}
