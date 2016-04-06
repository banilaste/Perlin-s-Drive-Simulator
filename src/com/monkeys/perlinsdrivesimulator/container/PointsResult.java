package com.monkeys.perlinsdrivesimulator.container;

public class PointsResult {
	public boolean absolute = false;
	public float points[];
	public int index;
	
	public PointsResult() {}
	
	public PointsResult(float points[], int index, boolean absolute) {
		this.points = points;
		this.index = index;
		this.absolute = absolute;
	}
}
