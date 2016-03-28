package com.monkeys.perlinsdrivesimulator;

import processing.core.PApplet;

public class Sol {
	private float points[], points2[];
	private int pointDistance, maxPoint , section ;
	
	public Sol(PApplet p) {
		points = new float[100];
		points2 = new float[100];
		pointDistance = 20;
		maxPoint = (int) (Math.ceil(p.width / pointDistance) + 2);
		section = 0;
		
		generate(p, section);
	}
	
	public void generate(PApplet p , int section ) {
		for (int index = 0; index < points.length; index += 1) {
			points[index] = p.noise((float) ((index + points.length*section ) * 0.02));
			points2[index] = p.noise((float) ((index + points.length*(section+1)) * 0.02));
		}
	}

	public void draw (Main p) {
		Voiture voiture = p.voiture;
		int CeKOnAffiche = ( p.width +(pointDistance*2 )) / pointDistance ;
		int positionx = (int) ( voiture.getPosition().x - p.width* 3/10 - section*pointDistance*points.length );
		int PointDepart = positionx / pointDistance  ;
		System.out.println(PointDepart);
		if( PointDepart > points.length ){
			PointDepart -= points.length ;
			section += 1 ;
			generate (p, section);
		}
		p.translate(section*pointDistance*points.length, 0);
	    p.stroke(255, 0, 255);
	    p.fill(200, 0, 200);
	    p.strokeWeight(3);
	    
	    // On commence à dessiner le sol
	    p.beginShape();
	    p.vertex(positionx , p.height * 2);
	    
	    // On dessine une line entre chaque point de la première section
	    if (PointDepart < 0 ){
	    	PointDepart = 0;
	    }
	    for (int index = PointDepart ; index < PointDepart + CeKOnAffiche && index-points.length < points2.length ; index += 1) {
	        
	        if(index >= points.length){
	        	 p.vertex((float) (this.pointDistance * index),
	 	        		(float) (this.points2[index-points.length] * p.height * 0.7 + p.height * 0.3));
	        }else{
	        	p.vertex((float) (this.pointDistance * index),
		        		(float) (this.points[index] * p.height * 0.7 + p.height * 0.3));
	        }
	    }
	    
	    p.vertex(p.width + positionx, p.height * 2);
	    p.endShape(p.CLOSE);
	};

}