package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PVector;

public class Roue {
	private int diametre; 
	private RelativePosition relPos;
	private PVector position;
	private Voiture parent;
	private boolean collideWithGroud = false;
	float [] points;
	Sol sol;
	
	public Roue(RelativePosition pos, Voiture parent) {
		diametre = 20;
		relPos = pos;
		this.parent = parent;
	}
	
	public void update(Main p) {
		// Récupération des points dans la portée du cercle
		position = relPos.update(parent.position, parent.getWidth(), parent.getHeight());
		PointsResult points = p.sol.getPointsInRange((int) Math.floor(position.x - diametre / 2), diametre, 1);
		
		// Pour chaque deux points, on vérifie si la roue entre en collision
		for (int index = 0; index < points.points.length - 1; index++) {
			
			// Si elle rentre en collision
			if (Collision.circleSegment(new PVector(p.sol.getIndexX(index + points.index), points.points[index]),
					new PVector(p.sol.getIndexX(index + points.index + 1), points.points[index + 1]),
					position, diametre / 2)) {
				
				// ON MET LA COLLISION A TRUE
				collideWithGroud = true;
				
				// TODO: remove
				parent.getSpeed().y = 0;
				
				break;
			}
			
			collideWithGroud = false;
		}
	}
	
	public void draw (PApplet p){
		p.fill(145, 226, 50);
		p.noStroke();
		p.ellipse(relPos.getRelativeX(parent.getWidth()), relPos.getRelativeY(parent.getHeight()), diametre, diametre);
		
		// Affichage d'info de collision
		/*if (collideWithGroud)
			p.fill(0, 255, 0);
		else
			p.fill(255, 0, 0);
		if (this.relPos == RelativePosition.LEFT) {
			p.text("Roue gauche : " + collideWithGroud, - parent.getWidth() / 2, -50);
		} else if (this.relPos == RelativePosition.RIGHT) {
			p.text("Roue droite : " + collideWithGroud, - parent.getWidth() / 2, -30);
		}*/
		
		// Dessin des lignes
		/*p.strokeWeight(1);
		p.stroke(250, 120, 120);
		
		p.pushMatrix();
		p.resetMatrix();

		p.translate(-parent.getPosition().x + p.width * 3/10, -parent.getPosition().y + p.height / 2);

		for (int x = 0, i = -2 + (int) (Math.floor(position.x - diametre / 2) - sol.offsetX) / sol.pointDistance; x < points.length; x++) {
			p.line(position.x, position.y,
					(i + x) * sol.pointDistance + sol.offsetX, points[x]);
		}
		p.popMatrix();*/
	}
}
