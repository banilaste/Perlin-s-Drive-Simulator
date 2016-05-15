package com.monkeys.perlinsdrivesimulator.scene.game;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.PointsResult;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Classe de gestion du sol
 * @author Banilaste
 *
 */
public class Ground {
	private float firstSection[], secondSection[];
	private int pointDistance, maxPoint, section, offsetX, sectionHeight, color, color2;

	public Ground(Main p) {
		// Cr�ation des sections (100 points par section)
		firstSection = new float[100];
		secondSection = new float[100];
		
		pointDistance = 20; // Distance entre chaque point
		onresize(p);
		sectionHeight = 1000; // Hauteur entre le point le plus haut et le plus bas
		
		// Num�ro de section
		section = 0;
		
		// D�calage depuis le d�but
		offsetX = 0;

		// Couleurs
		color = p.color(p.random(255), p.random(255), p.random(255));
		color2 = p.lerpColor(color, p.color(0), 0.1f);
		
		// G�n�ration de la premi�re section
		generate(p, section, false);
	}

	/**
	 * G�n�re une section de la map et la seconde selon
	 * @param p Objet processing
	 * @param section Num�ro de la section � g�n�rer
	 * @param reUse Utilisation des section actuelles ou non?
	 */
	public void generate(PApplet p, int section, boolean reUse) {
		// Si la seconde section est d�ja g�n�r�e
		if (reUse && section == this.section + 1) {
			firstSection = secondSection;
			secondSection = generateSingle(p, section + 1);
			
		// Sinon si la premi�re l'est
		} else if (reUse && section + 1 == this.section) {
			secondSection = firstSection;
			firstSection = generateSingle(p, section);
			
		// Sinon on g�n�re les deux
		} else {
			firstSection = generateSingle(p, section);
			secondSection = generateSingle(p, section + 1);
		}
	}

	/**
	 * G�n�re une seule section
	 * @param p
	 * @param section Num�ro de section
	 * @return
	 */
	private float[] generateSingle(PApplet p, int section) {
		float[] sectionPoints = new float[firstSection.length];
		
		for (int index = 0; index < firstSection.length; index += 1) {
			sectionPoints[index] = p.noise((index + firstSection.length * section) * 0.02f);
		}
		
		return sectionPoints;
	}
	
	/**
	 * Affichage du sol
	 */
	public void draw (Main p) {
		int startX = (int) (p.getGame().getPlayer().getPosition().x - p.width * 3/10 - offsetX),
			startPoint = startX / pointDistance - 1;

		// Si la premi�re partie de points est d�pass�e, on g�n�re la suivante
		if (startPoint > firstSection.length){
			System.out.println("Generating section " + (section + 1));
			
			startPoint -= firstSection.length;
			offsetX += firstSection.length * pointDistance;
			startX -= firstSection.length * pointDistance;
			
			generate(p, section + 1, true);
			
			section += 1;
			
		// Si la premi�re partie est trop � droite, on revient � la premi�re
		} else if (startPoint < 0) {
			System.out.println("Generating section " + (section - 1));
			
			startPoint += firstSection.length;
			offsetX -= firstSection.length * pointDistance;
			startX += firstSection.length * pointDistance;
			
			generate(p, section - 1, true);
			
			section -= 1;
		}
		
		
		
		// Sauvegarde du rep�re et translation
		p.pushMatrix();
		p.translate(offsetX, 0);
		
		// D�finition des styles
		p.stroke(color2);
		p.fill(color);
		p.strokeWeight(3);

		// On commence � dessiner le sol
		p.beginShape();
		p.vertex(startX, p.height * 2);
		
		for (int index = PApplet.max(0, startPoint);
				index < startPoint + maxPoint && index - firstSection.length < secondSection.length;
				index += 1) {

			// On dessine un point par point affichable
			p.vertex(this.pointDistance * index, getAbsoluteValueFromIndex(index));
		}

		p.vertex(p.width + startX, p.height * 2);
		p.endShape(PConstants.CLOSE);
		p.popMatrix();
	}
	
	/**
	 * Gestion du redimensionnement (changement du nombre de points maximum affichables
	 * @param p
	 */
	public void onresize(Main p) {
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 3);
	}
	
	/**
	 * R�cup�re la hauteur d'un point en valeur absolue
	 * @param index Num�ro du point
	 * @return
	 */
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
	
	/**
	 * R�cup�re la position d'un point en pixel selon son index
	 * @param index
	 * @return
	 */
	public float getPositionFromIndex(int index) {
		return index * pointDistance + offsetX;
	}
	
	/**
	 * Renvoie tous les points dans la port�e indiqu�e.
	 * @param x D�but de la section demand�e
	 * @param width Taille de la section
	 * @return
	 */
	public PointsResult getPointsInRange(int x, int width, int margin) {
		x = (int) Math.floor((x - offsetX) / pointDistance);
		width /= pointDistance;
		
		return new PointsResult(
				getPoints(x - margin, width + margin * 2),
				x - margin
		);
	}
	
	
	/**
	 * Renvoie n points depuis le start
	 * @param start Point de d�part
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

	public int getSection() {
		return section;
	}
}