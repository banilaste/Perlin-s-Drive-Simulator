package com.monkeys.perlinsdrivesimulator;


import processing.core.PConstants;
import processing.core.PVector;

public enum RelativePosition {
	LEFT(-0.5f, 0.5f), RIGHT(0.5f, 0.5f);
	
	float x, y;
	
	RelativePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public PVector update(PVector pos, int w, int h, float angle) {
		PVector gravityCenter = getGravityCenterLocation(w, h, angle);
		// NOTE : le +0.5 correspond à la translation de 50% de la taille de la voiture avant le dessin
		return pos.copy().add(gravityCenter).add(w * 0.5f, h * 0.5f);
	}

	
	public PVector getGravityCenterLocation(int width, int height, float angle) {
		return new PVector(width * x, height * y).rotate(angle);
		
		//return body.getPosition().copy().add(lastGravityCenterLocation.x + 0.5f * body.getSize().x, lastGravityCenterLocation.y + 0.5f * body.getSize().y);
	}
	
	/*public PVector getGravityCenterLocation(int width, int height) {
		return new PVector(width * x, height * y);
	}*/
	
	public float getRelativeX(int w) {
		return w * x;
	}
	
	public float getRelativeY(int h) {
		return h * y;
	}
}
