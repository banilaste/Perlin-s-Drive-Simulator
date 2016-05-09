package com.monkeys.perlinsdrivesimulator;

import com.monkeys.perlinsdrivesimulator.scene.Background;
import com.monkeys.perlinsdrivesimulator.scene.FocusableElement;
import com.monkeys.perlinsdrivesimulator.scene.GameOverScene;
import com.monkeys.perlinsdrivesimulator.scene.MainMenuScene;
import com.monkeys.perlinsdrivesimulator.scene.Scene;
import com.monkeys.perlinsdrivesimulator.scene.ServerSelectScene;
import com.monkeys.perlinsdrivesimulator.scene.game.GameScene;

import processing.core.PApplet;

public class Main extends PApplet {
	private Scene currentScene;
	private KeyListener keys;

	private int lastWidth = 0;
	private GameScene game;
	private MainMenuScene mainMenu;
	private ServerSelectScene serverSelect;
	private GameOverScene gameOver;
	
	public void settings() {
		size(800, 600);
		lastWidth = 800;
	}

	public void setup() {
		// Redimensionnement
		surface.setResizable(true);
		surface.setTitle("Perlin's Drive Simulator - An useless game !");
		
		// Gestion des touches fl�ch�es
		keys = new KeyListener();
		
		// Initialisation des sc�nes
		game = new GameScene(this);
		mainMenu = new MainMenuScene(this);
		serverSelect = new ServerSelectScene(this);
		gameOver = new GameOverScene();
		
		// D�finition de la sc�ne par d�faut
		currentScene = mainMenu;
		
		Background.changeBackgroundSettings(20, 0.001f);
		Background.onWindowResized(this);
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

	/**
	 * Change la sc�ne courante et active onresize pour pallier aux redimensionnements non pris en comptes
	 * @param scene
	 */
	public void setScene(Scene scene) {
		currentScene = scene;

		FocusableElement.currentFocus.unfocus();
		currentScene.onresize(this);
	}
	
	/*
	 * Fonctions utilis�es dans les sc�nes seules
	 */
	public void resize() {
		Background.onWindowResized(this);
		
		currentScene.onresize(this);
	}

	public void mousePressed() {
		currentScene.onclick(this);
	}
	
	public void mouseMoved() {
		currentScene.onmousemove(this);
	}
	
	public void keyPressed() {
		keys.onKeyPressed(this);
		currentScene.onkeytyped(this);
	}
	
	/*
	 * Gestion des touches
	 */

	public void keyReleased() {
		keys.onKeyReleased(this);
	}

	
	/*
	 * Getters
	 */
	public GameScene getGame() {
		return game;
	}

	public MainMenuScene getMainMenu() {
		return mainMenu;
	}

	public KeyListener getKeyListener() {
		return keys;
	}

	public Scene getServerSelect() {
		return serverSelect;
	}
	
	public GameOverScene getGameOver() {
		return gameOver;
	}
	
	// LE MAIN !
	public static void main(String args[]) {
		PApplet.main(new String[] {"com.monkeys.perlinsdrivesimulator.Main"});
	}
}
