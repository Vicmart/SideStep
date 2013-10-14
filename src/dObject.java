import java.awt.Color;


public class dObject {
	public double x = 0;
	public double y = 0;
	public double xVel = 0;
	public double yVel = 0;
	public double density = 1.0;
	public double width = 1.0;
	public double height = 1.0;
	public Color color = Color.BLUE;
	public boolean exploding = false;
	public boolean rocket = false;
	public double transparency = 1.0;
	public dObject(double x2, double y2, double xVel2, double yVel2, double density2){
		x = x2;
		y = y2;
		xVel = xVel2;
		yVel = yVel2;
		density = density2;
	}	
}
