import java.awt.Color;


public class sObject {
	public double x = 0;
	public double y = 0;
	public double xVel = 0;
	public double yVel = 0;
	public double density = 1.0;
	public double width = 5.0;
	public double height = 5000.0;
	public boolean exists = true;
	public boolean unstable = false;
	public Color color = new Color(102,51,0);
	public sObject(double x2, double y2){
		x = x2;
		y = y2;
	}	
}
