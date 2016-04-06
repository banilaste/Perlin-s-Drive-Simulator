import processing.core.PApplet;

public class Main extends PApplet {
	Sol sol;
	KeyListener keys;
	Voiture voiture;
	
	public void setup() {
		size(800, 600);

		sol = new Sol(this);
		keys = new KeyListener();
		voiture = new Voiture(this);
	}
	
	public void draw() {
		background(0);
		voiture.update(this);
		
		translate(-voiture.getPosition().x + width * 3 / 10, -voiture.getPosition().y + height / 2);
		
		voiture.draw(this);
		sol.draw(this);
		
	}
	
	public void keyPressed() {
		keys.onKeyPressed(this);
	}
	
	public void keyReleased() {
		keys.onKeyReleased(this);
	}
}
