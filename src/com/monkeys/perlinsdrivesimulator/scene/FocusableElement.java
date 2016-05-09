package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;

/**
 * Classe de base de tout composants, comporte la gestion
 * du focus (�l�ment s�l�ctionn�, �l�ment survol�)
 * @author Banilaste
 *
 */
public class FocusableElement extends Scene {
	public static FocusableElement currentFocus;
	protected static int mouseDefaultCursor = PConstants.ARROW;
	
	protected int mouseFocusCursor = PConstants.HAND;
	protected boolean hasMouseFocus, hasFocus;
	protected FocusableElement nextElement, previousElement;
	
	public FocusableElement(Main p) {
		super(p);
	}

	/**
	 * Fonction retournant true si le point donn� est sur le composant
	 * Cette fonction est r��crite pour chaque composant
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isPointIn(float x, float y) {
		return true;
	}
	
	/**
	 * Gestion du mouvement de la souris
	 */
	public void onmousemove(Main p) {
		// Si la souris se trouve au dessus de composant, on change le curseur et
		// on met le focus � true
		if (isPointIn(p.mouseX, p.mouseY)) {
			hasMouseFocus = true;
			p.cursor(mouseFocusCursor);
		
		// Sinon si la souris � le focus, on rechange le curseur
		} else if (hasMouseFocus) {
			p.cursor(mouseDefaultCursor);
			hasMouseFocus = false;
		}
	}
	
	/**
	 * Gestion du clic de la souris
	 */
	public void onclick(Main p) {
		// Si le clic est sur le composant, on demande le focus
		if (isPointIn(p.mouseX, p.mouseY)) {
			focus();
		
		// Sinon si on poss�de le focus, on l'annule
		} else if (hasFocus) {
			unfocus();
		}
	}
	
	/**
	 * Demande le focus sur le composant actuel
	 */
	public void focus() {
		// Met le focus � true
		hasFocus = true;
		
		// Si un composant est d�j� d�clar� comme ayant le focus (en global),
		// on lui retire le focus
		if (currentFocus != null && currentFocus != this) {
			currentFocus.unfocus();
		}
		
		// On s'auto-d�clare comme ayant le focus
		currentFocus = this;
	}
	
	/**
	 * Annule le focus
	 */
	public void unfocus() {
		// Met le focus � false
		hasFocus = false;
		
		// Si le focus est sur nous, on l'annule
		if (currentFocus == this) {
			currentFocus = null;
		}
	}
	
	/**
	 * Donne le focus � l'�l�ment suivant (si celui-ci est d�clar� et
	 * que l'on a le focus)
	 */
	public void focusNext() {
		if (nextElement != null && hasFocus) {
			nextElement.focus();
		}
	}
	/**
	 * Donne le focus � l'�l�ment pr�c�dent (si celui-ci est d�clar� et
	 * que l'on a le focus)
	 */
	public void focusPrevious() {
		if (previousElement != null && hasFocus) {
			previousElement.focus();
		}
	}
}
