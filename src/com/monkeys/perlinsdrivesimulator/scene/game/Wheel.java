package com.monkeys.perlinsdrivesimulator.scene.game;

import com.monkeys.perlinsdrivesimulator.Collision;
import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.CollisionResult;
import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PVector;

public class Wheel {
	private int diameter; 
	private RelativePosition relativePosition;
	private Player parent;
	private float minDistanceFromGround;
	
	public Wheel(RelativePosition pos, Player parent) {
		this.diameter = 20;
		this.relativePosition = pos;
		this.parent = parent;
	}
	
	public void update(Main p) {
		PVector gravityCenter = relativePosition.getGravityCenterLocation(parent.width, parent.height, parent.angle);
		
		// Récupération des points dans la portée du cercle
		PVector position = relativePosition.update(parent.getPlannedPosition(), parent.getWidth(), parent.getHeight(), parent.angle);
		PointsResult points = p.getGame().getGround().getPointsInRange(PApplet.floor(position.x - diameter / 2), diameter, 2);
		CollisionResult collision; // Objet résultant d'un calcul de collision
		float angle, force;
		
		minDistanceFromGround = 100;
		
		// Pour chaque deux points, on vérifie si la roue entre en collision
		for (int index = 0; index < points.points.length - 1; index++) {
			
			collision = Collision.circleSegment(
					new PVector(p.getGame().getGround().getIndexX(index + points.index), points.points[index]),
					new PVector(p.getGame().getGround().getIndexX(index + points.index + 1), points.points[index + 1]),
					position, diameter / 2
				);
			
			// Si elle rentre en collision
			if (collision != null) {
				
				if (collision.norm.mag() < minDistanceFromGround)
					minDistanceFromGround = collision.norm.mag();
				
				// On met le vecteur de collision dans le bon sens
				if (collision.norm.y > 0) {
					collision.norm.mult(-1);
				}

				// Calcul de l'angle puis de la force
				angle = PVector.angleBetween(collision.norm, parent.getSpeed());
				force = PApplet.abs(PApplet.cos(angle) * parent.speed.mag());
				
				// On utilie le vecteur normal à la collision pour la modification de vitesse
				collision.norm.setMag(force);
				
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
	
	public float getMinDistanceFromGround() {
		return minDistanceFromGround;
	}

	public int getRadius() {
		return diameter / 2;
	}
}
