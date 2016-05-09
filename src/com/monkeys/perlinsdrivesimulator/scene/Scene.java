package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

/**
 * Classe "mère" de toute scène
 * Les fonctions init() et draw() sont les fonctions principales
 * et sont appelées lors de l'initialisation du programme puis à
 * chaque frame.
 * @author Banilaste
 *
 */
public class Scene {
	protected boolean initialized = false;
	
	public Scene() {}
	
	public Scene(Main p) {
		init(p);
	}
	
	/**
	 * Initialise la scène
	 * @param p Object Main
	 */
	public void init(Main p) {
		initialized = true;
	}
	
	/**
	 * Affiche la scène
	 * @param p
	 */
	public void draw(Main p) {}
	
	public void onresize(Main p) {}
	public void onclick(Main p) {}
	public void onmousemove(Main p) {}
	public void onkeytyped(Main p) {}
}
