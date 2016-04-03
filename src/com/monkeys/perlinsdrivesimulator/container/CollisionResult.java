package com.monkeys.perlinsdrivesimulator.container;

import processing.core.PVector;

public class CollisionResult {
	public boolean collide = true;
	public PVector norm, point;
	public int direction;
	
	public CollisionResult(PVector norm, int direction) {
		this.norm = norm;
		this.direction = direction;
	}
	
	public CollisionResult(PVector norm, PVector point, int direction) {
		this.norm = norm;
		this.direction = direction;
		this.point = point;
	}
}
