package com.monkeys.perlinsdrivesimulator;

import java.io.IOException;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemotePlayer;
import com.monkeys.perlinsdrivesimulator.scene.Scene;
import com.monkeys.perlinsdrivesimulator.scene.game.Game;
import com.monkeys.perlinsdrivesimulator.scene.game.Ground;
import com.monkeys.perlinsdrivesimulator.scene.game.Player;

import processing.core.PApplet;

public class Main extends PApplet {
	private Scene currentScene;
	private KeyListener keys;
	
	boolean multiplayerEnabled = false;
	
	private int lastWidth = 0;
	private Game game;
	
	public void settings() {
		size(800, 600);
		lastWidth = 800;
	}

	public void setup() {
		// Redimensionnement
		surface.setResizable(true);
		
		// Gestion des touches flech�es
		keys = new KeyListener();
		
		// Cr�ation de la sc�ne de jeu
		game = new Game();
		game.init(this);
		
		// D�finition de la sc�ne par d�faut
		currentScene = game;
	}

	public void draw() {
		// Gestion du redimensionnement
		if (lastWidth != width) {
			resize();
		}
		
		lastWidth = width;
		
		// Mise � jour et dessin de la sc�ne
		currentScene.draw(this);
	}

	public void resize() {
		currentScene.onresize();
	}

	public void keyPressed() {
		keys.onKeyPressed(this);
	}

	public void keyReleased() {
		keys.onKeyReleased(this);
	}

	public Game getGame() {
		return game;
	}

	public KeyListener getKeyListener() {
		return keys;
	}
	
	// LE MAIN !
	public static void main(String args[]) {
		PApplet.main(new String[] {"com.monkeys.perlinsdrivesimulator.Main"});
	}
}
