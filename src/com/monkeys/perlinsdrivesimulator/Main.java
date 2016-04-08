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
		
		// Gestion des touches flech�es
		keys = new KeyListener();
		
		// Cr�ation de la sc�ne de jeu
		game = new GameScene();
		game.init(this);
		
		// Cr�ation du menu principal
		mainMenu = new MainMenuScene();
		mainMenu.init(this);
		
		// D�finition de la sc�ne par d�faut
		currentScene = mainMenu;
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

	public void setScene(Scene scene) {
		currentScene = scene;
	}
	
	/*
	 * Fonctions utilis�es dans les sc�nes seules
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
