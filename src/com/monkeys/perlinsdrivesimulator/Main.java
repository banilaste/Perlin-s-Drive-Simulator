package com.monkeys.perlinsdrivesimulator;

import java.io.IOException;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemotePlayer;
import com.monkeys.perlinsdrivesimulator.scene.Background;
import com.monkeys.perlinsdrivesimulator.scene.MainMenuScene;
import com.monkeys.perlinsdrivesimulator.scene.Scene;
import com.monkeys.perlinsdrivesimulator.scene.ServerSelectScene;
import com.monkeys.perlinsdrivesimulator.scene.game.GameScene;
import com.monkeys.perlinsdrivesimulator.scene.game.Ground;
import com.monkeys.perlinsdrivesimulator.scene.game.Player;

import processing.core.PApplet;

public class Main extends PApplet {
	private Scene currentScene;
	private KeyListener keys;
	
	private int lastWidth = 0;
	private GameScene game;
	private MainMenuScene mainMenu;
	private ServerSelectScene serverSelect;
	
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
		
		// D�finition de la sc�ne par d�faut
		currentScene = mainMenu;
		
		Background.changeBackgroundSetting(20, 0.001f);
		Background.onWindowsResized(this);
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
	 * Change la scene courante et active onresize pour pallier aux redimensionnements non pris en comptes
	 * @param scene
	 */
	public void setScene(Scene scene) {
		currentScene = scene;
		
		currentScene.onresize(this);
	}
	
	/*
	 * Fonctions utilis�es dans les sc�nes seules
	 */
	public void resize() {
		Background.onWindowsResized(this);
		
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
	
	// LE MAIN !
	public static void main(String args[]) {
		PApplet.main(new String[] {"com.monkeys.perlinsdrivesimulator.Main"});
	}
}
