package com.monkeys.perlinsdrivesimulator.scene;

import processing.core.PApplet;
import processing.core.PConstants;

public class Background {
	private static float angle = 0, size, stripNumber, stripAngle, rotationSpeed = 0.001f;
	
	/**
	 * Dessin du fond d'�cran
	 * @param p
	 */
	public static void drawAmazingBackground(PApplet p) {
		angle += rotationSpeed;
		
		p.pushMatrix();
		
		p.translate(p.width / 2, p.height / 2);
		p.rotate(angle);
		p.noStroke();

		for (int i = 0, color = 0; i < stripNumber; i ++) {
			
			if (color == 1) {
				p.fill(250, 120, 10);
			} else {
				p.fill(200, 80, 60);
			}
			
			p.triangle(0, 0, 0, size, PApplet.tan(stripAngle) * size, size);
			p.rotate(stripAngle);
			
			// Changement de couleur
			color = 1 - color;
		}
		
		p.popMatrix();
	}
	
	/**
	 * Change les param�tres du fond d'�cran
	 * @param stripNb Nombre de bandes
	 * @param rotationalSpeed Vitesse de rotation
	 */
	public static void changeBackgroundSettings(int stripNb, float rotationalSpeed) {
		stripNumber = stripNb;
		stripAngle = PConstants.TWO_PI / stripNumber;
		rotationSpeed = rotationalSpeed;
	}
	
	/**
	 * Gestion du redimensionnement de la fen�tre
	 * @param p
	 */
	public static void onWindowResized(PApplet p) {
		// Gestion de la taille maximale d'une bande
		size = Math.max(p.width, p.height);
	}
}
