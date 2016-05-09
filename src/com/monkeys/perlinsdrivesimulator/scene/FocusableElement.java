package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;

/**
 * Classe de base de tout composants, comporte la gestion
 * du focus (élément séléctionné, élément survolé)
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
	 * Fonction retournant true si le point donné est sur le composant
	 * Cette fonction est réécrite pour chaque composant
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
		// on met le focus à true
		if (isPointIn(p.mouseX, p.mouseY)) {
			hasMouseFocus = true;
			p.cursor(mouseFocusCursor);
		
		// Sinon si la souris à le focus, on rechange le curseur
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
		
		// Sinon si on possède le focus, on l'annule
		} else if (hasFocus) {
			unfocus();
		}
	}
	
	/**
	 * Demande le focus sur le composant actuel
	 */
	public void focus() {
		// Met le focus à true
		hasFocus = true;
		
		// Si un composant est déjé déclaré comme ayant le focus (en global),
		// on lui retire le focus
		if (currentFocus != null && currentFocus != this) {
			currentFocus.unfocus();
		}
		
		// On s'auto-déclare comme ayant le focus
		currentFocus = this;
	}
	
	/**
	 * Annule le focus
	 */
	public void unfocus() {
		// Met le focus à false
		hasFocus = false;
		
		// Si le focus est sur nous, on l'annule
		if (currentFocus == this) {
			currentFocus = null;
		}
	}
	
	/**
	 * Donne le focus à l'élément suivant (si celui-ci est déclaré et
	 * que l'on a le focus)
	 */
	public void focusNext() {
		if (nextElement != null && hasFocus) {
			nextElement.focus();
		}
	}
	/**
	 * Donne le focus à l'élément précédent (si celui-ci est déclaré et
	 * que l'on a le focus)
	 */
	public void focusPrevious() {
		if (previousElement != null && hasFocus) {
			previousElement.focus();
		}
	}
}
