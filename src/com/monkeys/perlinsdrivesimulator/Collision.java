package com.monkeys.perlinsdrivesimulator;

import processing.core.PVector;

public class Collision {
	public static boolean circleSegment(PVector pointA, PVector pointB, PVector circleCenter, int radius) {
		PVector ac, ab, pointI;
		double angle, ai;
	
		// Etape 1 : collisions points/cercle
		if (PVector.sub(pointA, circleCenter).mag() < radius) { // Point A <-> cercle
			return true;
		}
		
		if (PVector.sub(pointB, circleCenter).mag() < radius) { // Point B <-> cercle
			return true;
		}
		
		// Etape intermédiaire : calcul des vecteurs pour des vérifications futures
		ac = PVector.sub(circleCenter, pointA);
		ab = PVector.sub(pointB, pointA);
		
		pointI = ab.copy().setMag(ab.x * ac.x + ab.y * ac.y);
		
		ai = PVector.sub(pointI, pointA).mag();
		
		// Etape 2 : portée du segment
		if (ai < 0 || ai > ab.mag()) {
			return false;
		}
		
		// Etape 3 : collision segment/cercle
		if (PVector.sub(pointI, circleCenter).mag() < radius) {
			return true;
		}
		
		return false;
	}
}
