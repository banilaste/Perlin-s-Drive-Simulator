package com.monkeys.perlinsdrivesimulator;

import java.awt.Point;

import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Sol {
	private float points[], points2[];
	public int pointDistance, maxPoint, section, offsetX, sectionHeight;

	public Sol(PApplet p) {
		points = new float[100];
		points2 = new float[100];
		pointDistance = 20;
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 2);
		sectionHeight = 1000; // Hauteur entre le point le plus haut et le plus bas
		
		// Numéro de section
		section = 0;
		
		// Décalage
		offsetX = 0;

		generate(p, section);
	}

	public void generate(PApplet p , int section ) {
		for (int index = 0; index < points.length; index += 1) {
			points[index] = p.noise((float) ((index + points.length*section ) * 0.02));
			points2[index] = p.noise((float) ((index + points.length*(section+1)) * 0.02));
		}
	}

	public void draw (Main p) {
		int positionx = (int) (p.voiture.getPosition().x - p.width * 3/10 - offsetX);
		int PointDepart = positionx / pointDistance;

		// Si la première partie de points est dépassée
		if (PointDepart > points.length){
			System.out.println("Swaping to section " + (section + 1));
			PointDepart -= points.length;
			section += 1;
			offsetX += points.length * pointDistance;
			generate (p, section);
		}
		
		p.pushMatrix();
		p.translate(offsetX, 0);
		
		// Styles
		p.stroke(255, 0, 255);
		//p.noFill();
		p.fill(200, 0, 200);
		p.strokeWeight(3);

		// On commence à dessiner le sol
		p.beginShape();
		p.vertex(positionx , p.height * 2);

		// On dessine une line entre chaque point de la première section
		if (PointDepart < 0){
			PointDepart = 0;
		}
		for (int index = PApplet.max(0, PointDepart);
				index < PointDepart + maxPoint && index-points.length < points2.length;
				index += 1) {

			// On dessine un point par point affichable
			p.vertex(this.pointDistance * index, getAbsoluteValueFromIndex(index));
		}

		p.vertex(p.width + positionx, p.height * 2);
		p.endShape(PConstants.CLOSE);
		p.popMatrix();
	};

	
	public int getOffset() {
		return offsetX;
	}

	public int getIndexFromX(float x) {
		return (int) Math.floor((x - offsetX) / pointDistance);
	}
	
	public void resize(Main p) {
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 2);
	}
	
	private float getAbsoluteValueFromIndex(int index) {
		if (index < 0 || index >= this.points.length + this.points2.length) {
			return 0;
		}
		
		if (index < this.points.length) {
			return this.points[index] * sectionHeight;
			
		} else if (index < this.points2.length + this.points.length) {
			return this.points2[index - this.points2.length] * sectionHeight;
		}
		
		return 0;
	}
	
	private float getAbsoluteValue(float point) {
		return point * sectionHeight;
	}
	
	public float getIndexX(int index) {
		return index * pointDistance + offsetX;
	}
	
	/**
	 * Renvoie tous les points dans la portée indiquée.
	 * @param x Début de la section demandée
	 * @param width Taille de la section
	 * @return
	 */
	public PointsResult getPointsInRange(int x, int width) {
		x = (int) Math.floor((x - offsetX) / pointDistance);
		width /= pointDistance;
		
		return new PointsResult(
				getPoints(x, width),
				x,
				true
		);
	}
	
	/**
	 * Renvoie tous les points dans la portée indiquée.
	 * @param x Début de la section demandée
	 * @param width Taille de la section
	 * @return
	 */
	public PointsResult getPointsInRange(int x, int width, int margin) {
		x = (int) Math.floor((x - offsetX) / pointDistance);
		width /= pointDistance;
		
		return new PointsResult(
				getPoints(x - margin, width + margin * 2),
				x - margin,
				true
		);
	}
	
	
	/**
	 * Renvoie n points depuis le start
	 * @param start Point de départ
	 * @param number Nombre de points
	 * @param absolute Points en valeur absolue
	 * @return
	 */
	public float[] getPoints(int start, int number) {
		float[] points = new float[number];
		
		for (int i = start; i < start + number; i++) {
			points[i - start] = getAbsoluteValueFromIndex(i);
		}
		
		return points;
	}
}