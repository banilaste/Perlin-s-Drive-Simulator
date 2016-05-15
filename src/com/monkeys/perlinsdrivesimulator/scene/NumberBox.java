package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;

/**
 * Zone de texte réservée aux nombres
 * @author Banilaste
 *
 */
public class NumberBox extends TextBox {

	public NumberBox(Main p, String text) {
		super(p, text);
	}
	
	public void onkeytyped(Main p) {
		if (!hasFocus) return;
		
		if (p.keyCode == PConstants.BACKSPACE) {
			if (text.length() == 0) return;
			
			text = text.substring(0, text.length() - 1);
			return;
		
		} else if (p.key >= '0' && p.key <= '9') { // Si c'est un caractère numérique, on l'ajoute à la chaine
			text += p.key;
		}
	}
	
	/**
	 * Récupère l'entrée sous forme numérique
	 * @return
	 */
	public int getInteger() {
		if (text.length() == 0) return 0;
		
		return Integer.parseInt(getText());
	}

}
