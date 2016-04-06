package com.monkeys.perlinsdrivesimulator;

import java.io.IOException;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemotePlayer;

import processing.core.PApplet;

public class Main extends PApplet {
	public Sol sol;
	KeyListener keys;
	Voiture voiture;
	RemoteConnection multiplayer;

	boolean multiplayerEnabled = true;
	
	private int lastWidth = 0;
	
	public void settings() {
		size(800, 600);
		lastWidth = 800;
	}

	public void setup() {
		surface.setResizable(true);
		
		sol = new Sol(this);
		keys = new KeyListener();
		voiture = new Voiture(this);

		// Création d'un objet multijoueur (si le mode est activé)
		if (multiplayerEnabled) {
			try {
				multiplayer = new RemoteConnection("127.0.0.1", 25565, this);
			} catch (IOException e) {
				e.printStackTrace();
				multiplayerEnabled = false;
				return;
			}

			voiture.enableMultiplayerUpdate();
		}

		// Redimensionnement autorisé
		surface.setResizable(true);
	}

	public void draw() {
		
		if (lastWidth != width) {
			resize();
		}
		
		lastWidth = width;
		
		
		background(0);
		text(frameRate, 10, 10);
		
		voiture.update(this);

		translate(-voiture.getPosition().x + width * 3/10, -voiture.getPosition().y + height / 2);

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

	public void resize() {
		sol.resize(this);
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
