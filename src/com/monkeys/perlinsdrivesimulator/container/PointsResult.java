package com.monkeys.perlinsdrivesimulator.container;

/**
 * Résulatats d'une demande de points du sol
 * @author Banilaste
 *
 */
public class PointsResult {
	public float points[]; // Liste des points
	public int index; // Index de départ
	
	public PointsResult() {}
	
	public PointsResult(float points[], int index) {
		this.points = points;
		this.index = index;
	}
}
