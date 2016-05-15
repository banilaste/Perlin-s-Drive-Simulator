package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;
import processing.core.PVector;

/**
 * Zone de texte (composant graphique)
 * @author Banilaste
 *
 */
public class TextBox extends FocusableElement {
	protected PVector position, size;
	protected String text = "", emptyText = "Enter somthing :D";
	protected int textSize, focusBlinkDelay;
	
	public TextBox(Main p, String text) {
		super(p);
		
		this.emptyText = text;
	}
	
	public void init(Main p) {
		mouseFocusCursor = PConstants.TEXT;
		
		position = new PVector();
		size = new PVector();
	}
	
	public void draw(Main p) {
		// Rectangle
		p.stroke(40);
		p.strokeWeight(4);
		p.fill(90);
		
		p.rect(position.x, position.y, size.x, size.y);
		
		// Texte
		p.textSize(textSize);
		
		if (text.equals("")) {
			p.fill(180);
			p.text(emptyText, position.x + 10, position.y + size.y * 0.7f);
		} else {
			p.fill(220);
			p.text(text, position.x + 10, position.y + size.y * 0.7f);
		}
		
		// Curseur
		if (hasFocus) {
			focusBlinkDelay --;
			
			if (focusBlinkDelay <= 0) {
				focusBlinkDelay = 70;
			} else if (focusBlinkDelay <= 35) {
				p.rect(position.x + p.textWidth(text) + 10, position.y + size.y * 0.2f, 2, textSize);
			}
			
			
		}
	}
	
	/**
	 * Lors de l'appui sur une touche
	 */
	public void onkeytyped(Main p) {
		if (!hasFocus) return;
		
		// Retour arrière -> suppression d'un caractère
		if (p.keyCode == PConstants.BACKSPACE) {
			if (text.length() == 0) return;
			
			text = text.substring(0, text.length() - 1);
		} else if (p.key == PConstants.TAB) { // Tab -> On passe à l'élément suivant
			focusNext();
			
		} else if (p.key != PConstants.CODED) { // Sinon si la touche est affichable -> Ajout à la chaine
			text += p.key;
		}
	}
	
	/**
	 * Renvoie vrai si le point indiqué se trouve dans le rectangle formé par la zone de texte
	 */
	public boolean isPointIn(float x, float y) {
		// Vérificaition du clic pour x
		if (x < position.x || x > position.x + size.x)
			return false;
		
		// Puis pour y
		if (y < position.y || y > position.y + size.y)
			return false;
		
		return true;
	}
	
	// Setters
	public void setSize(float width, float height) {
		size.set(width, height);
		
		textSize = (int) (height * 0.6f);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	public String getText() {
		return text;
	}

	public void setText(String string) {
		text = string;
	}
}
