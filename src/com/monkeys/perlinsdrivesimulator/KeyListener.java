package com.monkeys.perlinsdrivesimulator;

import processing.core.PApplet;
import processing.core.PConstants;

public class KeyListener {
	public boolean left, right;
	
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
