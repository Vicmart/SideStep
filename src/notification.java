
public class notification {
	public String message = "";
	public double x = 0.0;
	public double xVel = 0.0;
	public double xAcel = 0.0;
	public double waiting = -1;
	public int id = 0;
	public notification(String a, int b){
		message = a;
		id = b;
		xAcel=0.00001;
		if(b>0){xVel=-0.11;}else{xVel=-0.07;}
	}
}
