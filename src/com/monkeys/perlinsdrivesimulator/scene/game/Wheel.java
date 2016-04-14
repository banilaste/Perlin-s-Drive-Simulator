package com.monkeys.perlinsdrivesimulator.scene.game;

import com.monkeys.perlinsdrivesimulator.Collision;
import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PVector;

public class Wheel {
	private int diameter; 
	private RelativePosition relativePosition;
	private Player parent;
	
	public Wheel(RelativePosition pos, Player parent) {
		this.diameter = 20;
		this.relativePosition = pos;
		this.parent = parent;
	}
	
	public void update(Main p) {
		PVector gravityCenter = relativePosition.getGravityCenterLocation(parent.width, parent.height, parent.angle);
		
		// Récupération des points dans la portée du cercle
		PVector position = relativePosition.getPosition(parent.getPlannedPosition(), parent.getWidth(), parent.getHeight(), parent.angle);
		PointsResult points = p.getGame().getGround().getPointsInRange(PApplet.floor(position.x - diameter / 2), diameter, 2);
		PVector collision; // Objet résultant d'un calcul de collision
		float angle, force;
		
		// Pour chaque deux points, on vérifie si la roue entre en collision
		for (int index = 0; index < points.points.length - 1; index++) {
			
			collision = Collision.circleSegment(
					new PVector(p.getGame().getGround().getPositionFromIndex(index + points.index), points.points[index]),
					new PVector(p.getGame().getGround().getPositionFromIndex(index + points.index + 1), points.points[index + 1]),
					position, diameter / 2
				);
			
			// Si elle rentre en collision
			if (collision != null) {
				
				// On met le vecteur de collision dans le bon sens
				if (collision.y > 0) {
					collision.mult(-1);
				}

				// Calcul de l'angle puis de la force
				angle = PVector.angleBetween(collision, parent.getSpeed());
				force = PApplet.abs(PApplet.cos(angle) * parent.speed.mag());
				
				// On utilie le vecteur normal à la collision pour la modification de vitesse
				collision.setMag(force);
				
				// On ajoute la vitesse de translation
				parent.speed.add(collision);
				
				// Modification de la vitesse de rotation (produit vectoriel)
				parent.rotationSpeed += (gravityCenter.x * collision.y - gravityCenter.y * collision.x) / 3000;
			}
			
		}
	}
	
	public void draw (PApplet p){
		p.fill(145, 226, 50);
		p.noStroke();
		p.ellipse(relativePosition.getRelativeX(parent.getWidth()), relativePosition.getRelativeY(parent.getHeight()), diameter, diameter);
	}
}
