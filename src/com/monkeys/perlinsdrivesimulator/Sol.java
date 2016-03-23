package com.monkeys.perlinsdrivesimulator;

import processing.core.PApplet;

public class Sol {
	private float points[];
	private int pointDistance, maxPoint;
	
	public Sol(PApplet p) {
		points = new float[100];
		pointDistance = 20;
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 2);
		
		generate(p);
	}
	
	public void generate(PApplet p) {
		for (int index = 0; index < points.length; index += 1) {
			points[index] = p.noise((float) (index * 0.02));
		}
	}

	public void draw (PApplet p) {
	    p.stroke(255, 0, 255);
	    p.fill(200, 0, 200);
	    p.strokeWeight(3);
	    
	    // On commence à dessiner le sol
	    p.beginShape();
	    p.vertex(0, p.height * 2);
	    
	    // On dessine une line entre chaque point de la première section
	    for (int index = 0; index < this.points.length; index += 1) {
	        p.vertex((float) (this.pointDistance * index),
	        		(float) (this.points[index] * p.height * 0.7 + p.height * 0.3));
	    }
	    
	    p.vertex(p.width, p.height * 2);
	    p.endShape(p.CLOSE);
	};

}