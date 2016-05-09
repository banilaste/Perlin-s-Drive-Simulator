package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

/**
 * Classe "m�re" de toute sc�ne
 * Les fonctions init() et draw() sont les fonctions principales
 * et sont appel�es lors de l'initialisation du programme puis �
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
	 * Initialise la sc�ne
	 * @param p Object Main
	 */
	public void init(Main p) {
		initialized = true;
	}
	
	/**
	 * Affiche la sc�ne
	 * @param p
	 */
	public void draw(Main p) {}
	
	public void onresize(Main p) {}
	public void onclick(Main p) {}
	public void onmousemove(Main p) {}
	public void onkeytyped(Main p) {}
}
