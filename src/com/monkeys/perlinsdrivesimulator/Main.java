package com.monkeys.perlinsdrivesimulator;

import java.io.IOException;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemotePlayer;
import com.monkeys.perlinsdrivesimulator.scene.MainMenuScene;
import com.monkeys.perlinsdrivesimulator.scene.Scene;
import com.monkeys.perlinsdrivesimulator.scene.game.GameScene;
import com.monkeys.perlinsdrivesimulator.scene.game.Ground;
import com.monkeys.perlinsdrivesimulator.scene.game.Player;

import processing.core.PApplet;

public class Main extends PApplet {
	private Scene currentScene;
	private KeyListener keys;
	
	boolean multiplayerEnabled = false;
	
	private int lastWidth = 0;
	private GameScene game;
	private MainMenuScene mainMenu;
	
	public void settings() {
		size(800, 600);
		lastWidth = 800;
	}

	public void setup() {
		// Redimensionnement
		surface.setResizable(true);
		
		// Gestion des touches flechées
		keys = new KeyListener();
		
		// Création de la scène de jeu
		game = new GameScene();
		game.init(this);
		
		// Création du menu principal
		mainMenu = new MainMenuScene();
		mainMenu.init(this);
		
		// Définition de la scène par défaut
		currentScene = mainMenu;
	}

	public void draw() {
		// Gestion du redimensionnement
		if (lastWidth != width) {
			resize();
		}
		
		lastWidth = width;
		
		// Mise à jour et dessin de la scène
		currentScene.draw(this);
	}

	public void setScene(Scene scene) {
		currentScene = scene;
	}
	
	/*
	 * Fonctions utilisées dans les scènes seules
	 */
	public void resize() {
		currentScene.onresize(this);
	}

	public void mouseClicked() {
		currentScene.onclick(this);
	}
	
	/*
	 * Gestion des touches
	 */
	public void keyPressed() {
		keys.onKeyPressed(this);
	}

	public void keyReleased() {
		keys.onKeyReleased(this);
	}

	public GameScene getGame() {
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
