package com.monkeys.perlinsdrivesimulator;

import java.io.IOException;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemotePlayer;

import processing.core.PApplet;

public class Main extends PApplet {
	Sol sol;
	KeyListener keys;
	Voiture voiture;
	RemoteConnection multiplayer;
	
	boolean multiplayerEnabled = true;
	
	public void settings() {
		size(800, 600);
	}
	
	public void setup() {
		sol = new Sol(this);
		keys = new KeyListener();
		voiture = new Voiture(this);
		
		// Création d'un objet multijoueur (si le mode est activé)
		if (multiplayerEnabled) {
			try {
				multiplayer = new RemoteConnection("82.229.128.18", 25565);
			} catch (IOException e) {
				e.printStackTrace();
				multiplayerEnabled = false;
				return;
			}
			
			voiture.enableMultiplayerUpdate();
		}
	}
	
	public void draw() {
		background(0);
		voiture.update(this);
		
		translate(-voiture.getPosition().x + width * 3 / 10, -voiture.getPosition().y + height / 2);
		
		voiture.draw(this);
		sol.draw(this);
		
		// Si le mode multijoueur est activé, on dessine aussi les voiture des autres
		// et on les met à jour
		if (multiplayerEnabled) {
			// On dessine tous les joueurs qu'importe leurs id
			for (RemotePlayer next : multiplayer.getPlayers().values()) {
				next.update(this);
				next.draw(this);
			}
		}
	}
	
	public void keyPressed() {
		keys.onKeyPressed(this);
	}
	
	public void keyReleased() {
		keys.onKeyReleased(this);
	}
	
	public static void main(String args[]) {
		PApplet.main(new String[] {"com.monkeys.perlinsdrivesimulator.Main"});
	}
}
