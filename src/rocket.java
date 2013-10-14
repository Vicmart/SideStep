import java.awt.Color;

public class rocket {
	public double x = 0.0;
	public double y = 0.0;
	public double xVel = 0.0;
	public double yVel = 0.0;
	public double width = 10;
	public double height = 10;
	public dObject target = null;
	public Color color = Color.red;
	public rocket(double a, double b){
		this.x = a;
		this.y = b;
	}
	public void target(world Wor){
		for(int f=0;f<Wor.dObjects.size();f++){
			dObject Com = Wor.dObjects.get(f);
			if(Math.sqrt(Math.pow(this.x-Com.x, 2) + Math.pow(this.y-Com.y, 2)) < 200 && Com.y < this.y + 30){
				this.target = Com;
			}
		}
	}
}
