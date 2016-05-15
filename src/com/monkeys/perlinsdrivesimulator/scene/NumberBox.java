package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;

/**
 * Zone de texte r�serv�e aux nombres
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
		
		} else if (p.key >= '0' && p.key <= '9') { // Si c'est un caract�re num�rique, on l'ajoute � la chaine
			text += p.key;
		}
	}
	
	/**
	 * R�cup�re l'entr�e sous forme num�rique
	 * @return
	 */
	public int getInteger() {
		if (text.length() == 0) return 0;
		
		return Integer.parseInt(getText());
	}

}
