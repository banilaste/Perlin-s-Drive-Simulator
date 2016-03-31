package com.monkeys.perlinsdrivesimulator;

import processing.core.PVector;

public enum RelativePosition {
	LEFT(-0.5f, 0.5f), RIGHT(0.5f, 0.5f);
	
	float x, y;
	
	RelativePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public PVector update(PVector pos, int w, int h) {
		// NOTE : le +0.5 correspond à la translation de 50% de la taille de la voiture avant le dessin
		return pos.copy().add((float) (w * (0.5 + x)), (float) (h * (0.5 + y)));
	}
	
	public float getRelativeX(int w) {
		return w * x;
	}
	
	public float getRelativeY(int h) {
		return h * y;
	}
}
