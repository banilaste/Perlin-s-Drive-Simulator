package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.scene.game.Ground;
import com.monkeys.perlinsdrivesimulator.scene.game.Player;

import processing.core.PApplet;
import processing.core.PConstants;

public class MainMenuScene extends Scene {
	protected float angle = 0, size, stripNumber, stripAngle;
	protected int titleTextSize, titleHalfWidth;
	protected String titleText = "Perlin's Drive Simulator", descriptionText = "Click to play :D";
	
	public void init(Main p) {
		if (initialized) {
			return;
		}
		
		onresize(p);
		stripNumber = 20;
		stripAngle = PConstants.TWO_PI / stripNumber;
		
		super.init(p);
	}
	
	public void draw(Main p) {
		drawBackground(p);
		
		p.stroke(0);
		p.fill(255);
		
		p.textSize(titleTextSize);
		p.text(titleText, p.width / 2 - titleHalfWidth, p.height * 0.2f);
		
		p.textSize(titleTextSize * 0.4f);
		p.text(descriptionText, p.width / 2 - p.textWidth(descriptionText) / 2, p.height * 0.8f);
	}
	
	public void drawBackground(PApplet p) {
		angle += 0.001f;
		
		p.pushMatrix();
		
		p.translate(p.width / 2, p.height / 2);
		p.rotate(angle);
		p.noStroke();

		for (int i = 0, color = 0; i < stripNumber; i ++) {
			
			if (color == 1) {
				p.fill(250, 120, 10);
			} else {
				p.fill(200, 80, 60);
			}
			
			p.triangle(0, 0, 0, size, PApplet.tan(stripAngle) * size, size);
			p.rotate(stripAngle);
			
			// Changement de couleur
			color = 1 - color;
		}
		
		p.popMatrix();
	}
	
	public void onresize(Main p) {
		// Gestion de la taille maximale (pour le fond d'écran)
		size = Math.max(p.width, p.height);
		
		// Calcul de la taille théorique du texte et de la largeur maximale
		float maxTitleWidth = p.width * 0.8f;
		titleTextSize = (int) (0.2f * p.height);
		
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
		p.setScene(p.getGame());
	}
}
