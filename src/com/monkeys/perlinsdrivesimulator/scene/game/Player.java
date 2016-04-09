package com.monkeys.perlinsdrivesimulator.scene.game;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RequestType;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Player {
	protected PVector speed, position, plannedPosition;
	protected int width , height, alternate = 0;
	protected float angle, rotationSpeed = 0;
	protected Wheel wheels[];
	protected String username = "";

	private boolean multiplayerUpdate = false;
	
	public Player (PApplet p) {
		speed = new PVector (0, 0);
		position = new PVector ();
		width = 60 ;
		height = 30;

		wheels = new Wheel[] {
			new Wheel(RelativePosition.LEFT, this),
			new Wheel(RelativePosition.RIGHT, this)
		};	
	}
	
	public void update (Main p){
		// Droite
		if (p.getKeyListener().right) {
			//speed.x += Math.cos(angle);
			//speed.y += Math.sin(angle);
			speed.x += 1;
		}

		// Gauche
		if (p.getKeyListener().left) {
			//speed.x -= Math.cos(angle);
			//speed.y -= Math.sin(angle);
			speed.x -= 1;
		}
		
		// Gravité
		speed.y += 0.2;
		
		// Limite de vitesse
		speed.x = PApplet.constrain(speed.x, -5, 5);
		speed.y = PApplet.constrain(speed.y, -5, 5);

		plannedPosition = position.copy().add(speed);
		
		// Mise à jour de la position des roues (on alterne
		// entre la roue gauche et droite pour la mise à jour
		// pour éviter des bugs)
		alternate = 1 - alternate;
		wheels[alternate].update(p);
		wheels[1 - alternate].update(p);
		
		position.add(speed);
		angle += rotationSpeed / 2;
		rotationSpeed *= 0.95;
		
		// Si la mise à jour multijoueur est activée, on envoie une
		// MaJ toutes les 4 frames
		if (multiplayerUpdate && p.frameCount % 4 == 0) {
			sendPositionUpdate(p.getGame().getMultiplayerInstance());
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
		
		// Dessin des roues
		wheels[0].draw(p);
		wheels[1].draw(p);
		
		// Et enfin le nom du joueur
		p.textSize(18);
		p.fill(255);
		p.text(username, -p.textWidth(username) / 2, -height / 2 - 9);
		
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

	public void setName(String text) {
		username = text;
	}
}
