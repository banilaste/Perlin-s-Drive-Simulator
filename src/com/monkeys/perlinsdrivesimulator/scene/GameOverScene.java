package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

/**
 * Scène de Game Over
 * @author Banilaste
 *
 */
public class GameOverScene extends Scene {
	private int titleTextSize;
	private int titleHalfWidth;
	private String titleText = "GAME OVER";

	public void draw(Main p) {
		p.background(0);
		
		// On se contente d'afficher le texte au center de l'écran à sa taille
		// calculée
		p.fill(255, 210, 210);
		p.textSize(titleTextSize);
		p.text(titleText, p.width / 2 - titleHalfWidth, p.height / 2 + titleTextSize * 0.4f);
	}


	public void onresize(Main p) {
		// Calcul de la taille théorique du texte et de la largeur maximale
		// le texte prendra 80% de la largeur maximum et 20% de la hauteur
		// minimum
		float maxTitleWidth = p.width * 0.8f;
		titleTextSize = (int) (0.2f * p.height);
		
		// Récupération de la taille réelle (avec un texte haut de 20%)
		p.textSize(titleTextSize);
		titleHalfWidth = (int) p.textWidth(titleText);
		
		// Création des valeurs finales
		if (titleHalfWidth > maxTitleWidth) {
			// Réduction de la hauteur du texte pour ne pas dépasser la largeur
			// maximum
			titleTextSize = (int) (titleTextSize * maxTitleWidth / titleHalfWidth);
			
			// Calcul de la moitié de la largeur du texte
			p.textSize(titleTextSize);
			titleHalfWidth = (int) (p.textWidth(titleText) / 2);
		
		} else {
			// Calcul de la moitié de la largeur du texte
			titleHalfWidth = titleHalfWidth / 2;
			
			// On utilise les 20% de la hauteur dans ce cas-ci
		}

	}
	
	public void onclick(Main p) {
		p.setScene(p.getMainMenu());
	}

}
