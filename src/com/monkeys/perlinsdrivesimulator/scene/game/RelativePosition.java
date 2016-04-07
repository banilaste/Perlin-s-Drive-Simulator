package com.monkeys.perlinsdrivesimulator.scene.game;

import processing.core.PVector;

public enum RelativePosition {
	LEFT(-0.5f, 0.5f), RIGHT(0.5f, 0.5f);
	
	float x, y;
	
	RelativePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public PVector update(PVector pos, int w, int h, float angle) {
		
		// NOTE : le +0.5 correspond à la translation de 50% de la taille de la voiture avant le dessin
		return pos.copy().add(getGravityCenterLocation(w, h, angle)).add(w * 0.5f, h * 0.5f);
	}
	
	public PVector getGravityCenterLocation(int width, int height, float angle) {
		return new PVector(width * x, height * y).rotate(angle);
	}
	
	public float getRelativeX(int w) {
		return w * x;
	}
	
	public float getRelativeY(int h) {
		return h * y;
	}
}
