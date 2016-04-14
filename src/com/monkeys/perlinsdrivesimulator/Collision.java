package com.monkeys.perlinsdrivesimulator;

import processing.core.PVector;

public class Collision {
	public static PVector circleSegment(PVector pointA, PVector pointB, PVector circleCenter, int radius) {
		PVector ac, ab, pointI, ai, ic;
		double angle;
		
		// Etape intermédiaire : calcul des vecteurs pour des vérifications futures
		ac = PVector.sub(circleCenter, pointA);
		ab = PVector.sub(pointB, pointA);
		angle = PVector.angleBetween(ac, ab);
		
		ai = ab.copy().mult((float) (Math.cos(angle) * ac.mag() / ab.mag()));

		pointI = PVector.add(ai, pointA);
		
		// Etape 1 : collisions points/cercle
		if (ac.mag() < radius) { // Point A <-> cercle
			return PVector.sub(circleCenter, pointI);
		}
		
		if (PVector.sub(pointB, circleCenter).mag() < radius) { // Point B <-> cercle
			return PVector.sub(circleCenter, pointI);
		}
		
		
		// Etape 2 : portée du segment
		if (ai.mag() > ab.mag() + radius) {
			return null;
		}

		ic = PVector.sub(circleCenter, pointI);
		
		// Etape 3 : collision segment/cercle
		if (ic.mag() < radius) {
			return ic;
		}

		return null;
	}
	
	public static PVector rectSegment(PVector pointA, PVector pointB, PVector position,  PVector size) {
		return null;
	}
}
