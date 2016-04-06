package com.monkeys.perlinsdrivesimulator;

import java.awt.Point;

import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Sol {
	private float firstSection[], secondSection[];
	public int pointDistance, maxPoint, section, offsetX, sectionHeight;

	public Sol(PApplet p) {
		firstSection = new float[100];
		secondSection = new float[100];
		
		pointDistance = 20;
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 2);
		sectionHeight = 1000; // Hauteur entre le point le plus haut et le plus bas
		
		// Numéro de section
		section = 0;
		
		// Décalage
		offsetX = 0;

		generate(p, section, false);
	}

	/**
	 * Génère une section de la map
	 * @param p Objet processing
	 * @param section Numéro de la section à générer
	 * @param reuse Utilisation des section actuelles ou non?
	 */
	public void generate(PApplet p, int section, boolean reuse) {
		// Si la seconde section est déja générée
		if (reuse && section - 1 == this.section) {
			firstSection = secondSection;
			secondSection = generateSingle(p, section + 1);
		// Sinon si la première l'est
		} else if (reuse && section == this.section - 1) {
			secondSection = firstSection;
			firstSection = generateSingle(p, section);
			
		// Sinon on génère les deux
		} else {
			firstSection = generateSingle(p, section);
			secondSection = generateSingle(p, section + 1);
		}
	}

	private float[] generateSingle(PApplet p, int section) {
		float[] sectionPoints = new float[firstSection.length];
		
		for (int index = 0; index < firstSection.length; index += 1) {
			sectionPoints[index] = p.noise((index + firstSection.length * section) * 0.02f);
		}
		
		return sectionPoints;
	}
	
	public void draw (Main p) {
		int startX = (int) (p.voiture.getPosition().x - p.width * 3/10 - offsetX),
			startPoint = startX / pointDistance;

		// Si la première partie de points est dépassée  (3 points de marge)
		if (startPoint > firstSection.length){
			System.out.println("Swaping to section " + (section + 1));
			
			startPoint -= firstSection.length;
			offsetX += firstSection.length * pointDistance;
			
			generate(p, section + 1, true);
			
			section += 1;
			
		// Si la première partie est trop à droite (3 points de marge)
		} else if (startPoint < 0) {
			System.out.println("Swaping to section " + (section - 1));
			
			startPoint += firstSection.length;
			offsetX -= firstSection.length * pointDistance;
			
			generate(p, section - 1, true);
			
			section -= 1;
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
		p.vertex(startX, p.height * 2);
		
		for (int index = PApplet.max(0, startPoint);
				index < startPoint + maxPoint && index-firstSection.length < secondSection.length;
				index += 1) {

			// On dessine un point par point affichable
			p.vertex(this.pointDistance * index, getAbsoluteValueFromIndex(index));
		}

		p.vertex(p.width + startX, p.height * 2);
		p.endShape(PConstants.CLOSE);
		p.popMatrix();
	};

	public int getIndexFromX(float x) {
		return (int) Math.floor((x - offsetX) / pointDistance);
	}
	
	public void resize(Main p) {
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 3);
	}
	
	private float getAbsoluteValueFromIndex(int index) {
		if (index < 0 || index >= this.firstSection.length + this.secondSection.length) {
			return 0;
		}
		
		if (index < this.firstSection.length) {
			return this.firstSection[index] * sectionHeight;
			
		} else if (index < this.secondSection.length + this.firstSection.length) {
			return this.secondSection[index - this.secondSection.length] * sectionHeight;
		}
		
		return 0;
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

	
	public int getOffset() {
		return offsetX;
	}

	public int getSection() {
		return section;
	}
}