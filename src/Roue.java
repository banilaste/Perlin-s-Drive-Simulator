import processing.core.PApplet;
import processing.core.PVector;

public class Roue {
	private int diametre; 
	
	public Roue() {
		diametre = 20;
	}
	
	public void draw (PApplet p){
		p.fill(145, 226, 50);
		p.noStroke();
		p.ellipse(0, 0, diametre, diametre);
	}
}
