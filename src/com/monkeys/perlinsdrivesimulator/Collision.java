package com.monkeys.perlinsdrivesimulator;

import processing.core.PVector;

/**
 * Classe de gestion des collisions
 * @author Banilaste
 *
 */
public class Collision {
	/**
	 * Gestion de collision cercle/segment
	 * @param pointA Premier point du segment
	 * @param pointB Second poitn du segment
	 * @param circleCenter Centre du cerlce
	 * @param radius Rayon du cercle
	 * @return Vecteur normal à la collision
	 */
	public static PVector circleSegment(PVector pointA, PVector pointB, PVector circleCenter, int radius) {
		PVector ac, ab, pointI, ai, ic;
		double angle;
		
		/*
		 * Etape intermédiaire : calcul des vecteurs pour des vérifications futures
		 */
		ac = PVector.sub(circleCenter, pointA);
		ab = PVector.sub(pointB, pointA);
		angle = PVector.angleBetween(ac, ab);
		
		// Vecteur allant de A vers le prejeté orthogonal de C sur AB (trigo)
		ai = ab.copy().mult((float) (Math.cos(angle) * ac.mag() / ab.mag()));

		// Calcul du point I
		pointI = PVector.add(ai, pointA);
		
		/*
		 * Etape 1 : collisions points/cercle
		 * Il y a collision si la distance d'un point au cercle est plus petite que le
		 * rayon
		 */
		if (ac.mag() < radius) { // Point A <-> cercle
			return PVector.sub(circleCenter, pointI);
		}
		
		if (PVector.sub(pointB, circleCenter).mag() < radius) { // Point B <-> cercle
			return PVector.sub(circleCenter, pointI);
		}
		
		
		/*
		 * Etape 2 : portée du segment
		 */
		if (ai.mag() > ab.mag() + radius) {
			return null;
		}

		ic = PVector.sub(circleCenter, pointI);
		
		/*
		 * Etape 3 : collision segment/cercle
		 * Si la distance du centre du cercle au segment (CI) est inférieure au rayon,
		 * il y a collision
		 */
		if (ic.mag() < radius) {
			return ic;
		}

		return null;
	}
	
	/**
	 * Collision rectangle/segment (non implémentée)
	 * @param pointA
	 * @param pointB
	 * @param position
	 * @param size
	 * @return
	 */
	public static PVector rectSegment(PVector pointA, PVector pointB, PVector position,  PVector size) {
		return null;
	}
}
