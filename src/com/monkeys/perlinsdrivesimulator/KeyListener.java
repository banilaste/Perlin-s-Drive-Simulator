package com.monkeys.perlinsdrivesimulator;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Classe de gestion des touches
 * @author Banilaste
 *
 */
public class KeyListener {
	public boolean left, right; // Seules les touches gauche/droite sont nécessaires dans notre cas
	
	/**
	 * Lors de l'appui sur une touche
	 */
	public void onKeyPressed (PApplet p) {
		switch(p.keyCode) {
		// Gauche
		case PConstants.LEFT:
			left = true;
			break;
			
		// Droite
		case PConstants.RIGHT:
			right = true;
			break;
		}
	}
	
	/**
	 * Lors du relachement d'une touche
	 */
	public void onKeyReleased(PApplet p) {
		switch(p.keyCode) {
		// Gauche
		case PConstants.LEFT:
			left = false;
			break;
			
		// Droite
		case PConstants.RIGHT:
			right = false;
			break;
		}
	}
	
}
