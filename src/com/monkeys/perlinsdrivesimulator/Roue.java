package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.container.CollisionResult;
import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PVector;

public class Roue {
	private int diameter; 
	private RelativePosition relativePosition;
	private Voiture parent;
	
	public Roue(RelativePosition pos, Voiture parent) {
		this.diameter = 20;
		this.relativePosition = pos;
		this.parent = parent;
	}
	
	public void update(Main p) {
		PVector gravityCenter = relativePosition.getGravityCenterLocation(parent.width, parent.height, parent.angle);
		
		// Récupération des points dans la portée du cercle
		PVector position = relativePosition.update(parent.getPlannedPosition(), parent.getWidth(), parent.getHeight(), parent.angle);
		PointsResult points = p.sol.getPointsInRange(PApplet.floor(position.x - diameter / 2), diameter, 2);
		CollisionResult collision; // Objet résultant d'un calcul de collision
		float angle, force;
		
		// Pour chaque deux points, on vérifie si la roue entre en collision
		for (int index = 0; index < points.points.length - 1; index++) {
			
			collision = Collision.circleSegment(new PVector(p.sol.getIndexX(index + points.index), points.points[index]),
					new PVector(p.sol.getIndexX(index + points.index + 1), points.points[index + 1]),
					position, diameter / 2);
			
			// Si elle rentre en collision
			if (collision != null) {
				
				// On met le vecteur de collision dans le bon sens
				if (collision.norm.y > 0) {
					collision.norm.mult(-1);
				}

				// Calcul de l'angle puis de la force
				angle = PVector.angleBetween(collision.norm, parent.getSpeed());
				force = PApplet.abs(PApplet.cos(angle) * parent.speed.mag());
				
				// On utilie le vecteur normal à la collision pour la modification de vitesse
				collision.norm.setMag(force * (1 + 0.01f * (diameter - collision.norm.mag()) / collision.norm.mag()));
				
				// On ajoute la vitesse de translation
				parent.speed.add(collision.norm);
				
				// Modification de la vitesse de rotation (produit vectoriel)
				parent.rotationSpeed += (gravityCenter.x * collision.norm.y - gravityCenter.y * collision.norm.x) / 3000;
			}
			
		}
	}
	
	public void draw (PApplet p){
		p.fill(145, 226, 50);
		p.noStroke();
		p.ellipse(relativePosition.getRelativeX(parent.getWidth()), relativePosition.getRelativeY(parent.getHeight()), diameter, diameter);
	}
}
