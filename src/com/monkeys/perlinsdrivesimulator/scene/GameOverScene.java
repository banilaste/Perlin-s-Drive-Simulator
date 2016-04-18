package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

public class GameOverScene extends Scene {
	private int titleTextSize;
	private int titleHalfWidth;
	private String titleText = "GAME OVER";

	public void draw(Main p) {
		if (!initialized) {
			onresize(p);
			initialized = true;
		}

		p.background(0);
		
		p.fill(255, 210, 210);
		p.textSize(titleTextSize);
		p.text(titleText, p.width / 2 - titleHalfWidth, p.height / 2 + titleTextSize * 0.4f);
	}


	public void onresize(Main p) {
		// Calcul de la taille théorique du texte et de la largeur maximale
		float maxTitleWidth = p.width * 0.8f;
		titleTextSize = (int) (0.8f * p.height);

		// Récupération de la taille réelle
		p.textSize(titleTextSize);
		titleHalfWidth = (int) p.textWidth(titleText);

		// Création des valeurs finales
		if (titleHalfWidth > maxTitleWidth) {
			titleTextSize = (int) (titleTextSize * maxTitleWidth / titleHalfWidth);

			p.textSize(titleTextSize);
			titleHalfWidth = (int) (p.textWidth(titleText) / 2);

		} else {
			titleHalfWidth = titleHalfWidth / 2;
		}

	}
	
	public void onclick(Main p) {
		p.setScene(p.getMainMenu());
	}
	
	public void onmousemove(Main p) {}
	public void onkeytyped(Main p) {}

}
