package com.monkeys.perlinsdrivesimulator.scene.game;

import processing.core.PVector;

/**
 * Classe de gestion de la position relative de la roue par rapport � la voiture
 * @author Banilaste
 *
 */
public enum RelativePosition {
	LEFT(-0.5f, 0.5f), RIGHT(0.5f, 0.5f);
	
	float x, y;
	
	RelativePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * R�cup�re la position de la roue en fonction de la position de la voiture
	 * @param pos Position de la voiture
	 * @param w Largeur de la voiture
	 * @param h Hauteur de la voiture
	 * @param angle Angle de la voiture
	 * @return
	 */
	public PVector getPosition(PVector pos, int w, int h, float angle) {
		
		// NOTE : le +0.5 correspond � la translation de 50% de la taille de la voiture avant le dessin
		return pos.copy().add(getGravityCenterLocation(w, h, angle)).add(w * 0.5f, h * 0.5f);
	}
	
	/**
	 * R�cup�re la position relative du centre de gravit�
	 * @param width Largeur de la voiture
	 * @param height Hauteur de la voiture
	 * @param angle Angle de la voiture
	 * @return
	 */
	public PVector getGravityCenterLocation(int width, int height, float angle) {
		return new PVector(width * x, height * y).rotate(angle);
	}
}
