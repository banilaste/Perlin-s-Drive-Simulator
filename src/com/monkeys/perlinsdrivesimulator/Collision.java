package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.container.CollisionResult;

import processing.core.PApplet;
import processing.core.PVector;

public class Collision {
	public static CollisionResult circleSegment(PVector pointA, PVector pointB, PVector circleCenter, int radius) {
		PVector ac, ab, pointI, ai;
		double angle;
		
		// Etape interm�diaire : calcul des vecteurs pour des v�rifications futures
		ac = PVector.sub(circleCenter, pointA);
		ab = PVector.sub(pointB, pointA);
		angle = PVector.angleBetween(ac, ab);
		
		ai = ab.copy().mult((float) (Math.cos(angle) * ac.mag() / ab.mag()));

		pointI = PVector.add(ai, pointA);
		
		// Etape 1 : collisions points/cercle
		if (PVector.sub(pointA, circleCenter).mag() < radius) { // Point A <-> cercle
			return new CollisionResult(PVector.sub(circleCenter, pointI), 1);
		}
		
		if (PVector.sub(pointB, circleCenter).mag() < radius) { // Point B <-> cercle
			return new CollisionResult(PVector.sub(circleCenter, pointI), 1);
		}
		
		
		// Etape 2 : port�e du segment
		// TODO: fix this !
		if (ai.mag() > ab.mag() + radius) {
			return null;
		}

		
		// Etape 3 : collision segment/cercle
		if (PVector.sub(pointI, circleCenter).mag() < radius) {
			return new CollisionResult(PVector.sub(circleCenter, pointI), 1);
		}

		return null;
	}
}