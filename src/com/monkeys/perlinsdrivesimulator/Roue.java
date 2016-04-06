package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.container.CollisionResult;
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
	PVector points2[];
	
	PVector force;
	
	public Roue(RelativePosition pos, Voiture parent) {
		diametre = 20;
		relPos = pos;
		this.parent = parent;
	}
	
	public void update(Main p) {
		// Récupération des points dans la portée du cercle
		position = relPos.update(parent.getPlannedPosition(), parent.getWidth(), parent.getHeight(), parent.getPlannedAngle());
		PointsResult points = p.sol.getPointsInRange((int) Math.floor(position.x - diametre / 2), diametre, 2);
		CollisionResult collision;
		
		points2 = new PVector[points.points.length - 1];
		this.points = points.points;
		this.sol = p.sol;
		
		// Pour chaque deux points, on vérifie si la roue entre en collision
		for (int index = 0; index < points.points.length - 1; index++) {
			
			collision = Collision.circleSegment(new PVector(p.sol.getIndexX(index + points.index), points.points[index]),
					new PVector(p.sol.getIndexX(index + points.index + 1), points.points[index + 1]),
					position, diametre / 2);
			
			// Si elle rentre en collision
			if (collision != null) {
				
				// ON MET LA COLLISION A TRUE
				collideWithGroud = true;
				
				if (collision.direction > 0) {
					collision.norm.mult(-1);
				}

				double angle = PVector.angleBetween(collision.norm, parent.getSpeed());
				float force = (float) (Math.cos(angle) * parent.speed.mag());
				PVector gravityCenter = relPos.getGravityCenterLocation(parent.width, parent.height, parent.getPlannedAngle());

				collision.norm.mult(-1).setMag(force * (1 + 0.05f * (diametre / 2 - collision.norm.mag()) / collision.norm.mag()));
				this.force = collision.norm.copy();
				
				parent.speed.add(collision.norm);
				
				//parent.rotationSpeed = (gravityCenter.x * collision.norm.y - gravityCenter.y * collision.norm.x);
				
				
				//break;
				if (collision.point != null) {
					points2[index] = collision.point;
				} else {
					points2[index] = new PVector(0, 0);
				}
			} else {
				points2[index] = new PVector(0, 0);
				collideWithGroud = false;
			}
			
		}
	}
	
	public void draw (PApplet p){
		p.fill(145, 226, 50);
		p.noStroke();
		p.ellipse(relPos.getRelativeX(parent.getWidth()), relPos.getRelativeY(parent.getHeight()), diametre, diametre);
		
		p.stroke(255, 255, 120);
		p.line(0, 0, parent.speed.x, parent.speed.y);
		
		if (this.force != null) {
			p.stroke(255, 120, 120);
			p.line(0, 0, force.x, force.y);
		}
		// Affichage d'info de collision
		if (collideWithGroud)
			p.fill(0, 255, 0);
		else
			p.fill(255, 0, 0);
		if (this.relPos == RelativePosition.LEFT) {
			p.text("Roue gauche : " + collideWithGroud, - parent.getWidth() / 2, -50);
		} else if (this.relPos == RelativePosition.RIGHT) {
			p.text("Roue droite : " + collideWithGroud, - parent.getWidth() / 2, -30);
		}
		
		// Dessin des lignes
		p.strokeWeight(1);
		p.stroke(250, 120, 120);
		
		p.pushMatrix();
		p.resetMatrix();

		p.translate(-parent.getPosition().x + p.width * 3/10, -parent.getPosition().y + p.height / 2);

		for (int x = 0, i = -2 + (int) (Math.floor(position.x - diametre / 2) - sol.offsetX) / sol.pointDistance; x < points.length; x++) {
			p.line(position.x, position.y,
					(i + x) * sol.pointDistance + sol.offsetX, points[x]);
		}
		
		/*p.stroke(120, 155, 250);
		for (int x = 0; x < points2.length; x++) {
			p.line(points2[x].x, points2[x].y, position.x, position.y);
		}*/
		p.popMatrix();
	}
}
