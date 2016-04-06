import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Voiture {
	private PVector speed , position;
	private int width , height ;
	private float angle ;
	private Roue roue[];

	public Voiture (PApplet p) {
		speed = new PVector (0, 0);
		position = new PVector ();
		width = 60 ;
		height = 30;

		roue = new Roue[] {
			new Roue(),
			new Roue()
		};	
	}
	
	public void update (Main p){
		// Gauche
		if (p.keys.left) {
			speed.x -= 1;
		}

		// Droite
		if (p.keys.right) {
			speed.x += 1;
		}

		// Haut
		if (p.keys.up) {
			speed.y -= 1;
		}

		// Bas
		if (p.keys.down) {
			speed.y += 1;
		}
		
		// gravité
		speed.y += 0.2;
		
		//limite de vitesse
		speed.x = p.constrain(speed.x, -5, 5);
		speed.y = p.constrain(speed.y, -5, 5);

		position.add(speed);
	}
	
	public void draw (PApplet p){
		p.pushMatrix();

		// Translation vers le centre de la voiture puis rotation
		p.translate( position.x + width / 2, position.y + height / 2);
		p.rotate(angle);

		// Dessin du joli rectangle
		p.fill(54, 127, 50);
		p.noStroke();
		p.rect(-width / 2, -height / 2, width , height);
		
		// Dessin des roue
		p.translate(-width / 2, height / 2);
		roue[0].draw(p);
		
		p.translate(width, 0);
		roue[1].draw(p);
		
		p.popMatrix();
	}

	// Getter
	public PVector getPosition() {
		return position;
	}
}
