package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RequestType;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Voiture {
	protected PVector speed, position, plannedPosition;
	protected int width , height, alternate = 0;
	protected float angle, rotationSpeed = 0;
	protected Roue roues[];
	private boolean multiplayerUpdate = false;
	
	public Voiture (PApplet p) {
		speed = new PVector (0, 0);
		position = new PVector ();
		width = 60 ;
		height = 30;

		roues = new Roue[] {
			new Roue(RelativePosition.LEFT, this),
			new Roue(RelativePosition.RIGHT, this)
		};	
	}
	
	public void update (Main p){
		// Droite
		if (p.keys.right) {
			speed.x += 1;
		}

		// Bas
		if (p.keys.left) {
			speed.x -= 1;
		}
		
		// Gravité
		speed.y += 0.2;
		//speed.x *= 0.9;
		
		// Limite de vitesse
		speed.x = PApplet.constrain(speed.x, -5, 5);
		speed.y = PApplet.constrain(speed.y, -5, 5);

		plannedPosition = position.copy().add(speed);
		
		// Mise à jour de la position des roues (on alterne
		// entre la roue gauche et droite pour la mise à jour
		// pour éviter des bugs)
		alternate = 1 - alternate;
		roues[alternate].update(p);
		roues[1 - alternate].update(p);
		
		position.add(speed);
		angle += rotationSpeed / 2;
		rotationSpeed *= 0.95;
		
		// Si la mise à jour multijoueur est activée, on envoie une
		// MaJ toutes les 4 frames
		if (multiplayerUpdate && p.frameCount % 4 == 0) {
			sendPositionUpdate(p.multiplayer);
		}
		
	}
	
	public void draw (PApplet p){
		p.pushMatrix();

		// Translation vers le centre de la voiture puis rotation
		p.translate(position.x + width / 2, position.y + height / 2);
		p.rotate(angle);

		// Dessin du joli rectangle
		p.fill(54, 127, 50);
		p.noStroke();
		p.rect(-width / 2, -height / 2, width , height);
		
		// Dessin des roue
		roues[0].draw(p);
		roues[1].draw(p);
		
		p.popMatrix();
	}
	
	public void sendPositionUpdate(RemoteConnection connection) {
		// On envoie la vitesse également pour permettre de donner un semblant de fluidité aux mises à jour
		connection.send(RequestType.POSITION, 0,
			position.x + " " + position.y + " " + speed.x + " " + speed.y + " " + angle + " " + rotationSpeed
		);
	}

	// Getters/setters
	public PVector getPosition() {
		return position;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void enableMultiplayerUpdate() {
		multiplayerUpdate = true;
	}

	public PVector getSpeed() {
		return speed;
	}
	
	public PVector getPlannedPosition() {
		return plannedPosition;
	}
}
