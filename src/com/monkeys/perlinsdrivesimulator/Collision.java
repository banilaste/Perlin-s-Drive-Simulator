package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.container.CollisionResult;

import processing.core.PVector;

public class Collision {
	public static CollisionResult circleSegment(PVector pointA, PVector pointB, PVector circleCenter, int radius) {
		PVector ac, ab, pointI, ai, ic;
		double angle;
		
		// Etape intermédiaire : calcul des vecteurs pour des vérifications futures
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
		
		
		// Etape 2 : portée du segment
		if (ai.mag() > ab.mag() + radius) {
			return null;
		}

		ic = PVector.sub(circleCenter, pointI);
		
		// Etape 3 : collision segment/cercle
		if (ic.mag() < radius) {
			return new CollisionResult(ic, 1);
		}

		return null;
	}
	
	public static CollisionResult rectSegment(PVector pointA, PVector pointB, PVector position,  PVector size) {
		return null;
	}
}
