package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.Callback;

public class MainMenuScene extends Scene {
	protected int titleTextSize, titleHalfWidth;
	protected String titleText;
	private Button playButton, serverSelectButton;
	
	public MainMenuScene(Main main) {
		super(main);
	}

	public void init(Main p) {
		if (initialized) {
			return;
		}
		
		// Textes
		titleText = "Perlin's Drive Simulator";
		
		// Boutons
		playButton = new Button(p, "Play !", new Callback() {
			public void run() {
				p.setScene(p.getGame());
			}
		});

		serverSelectButton = new Button(p, "Multiplayer !", new Callback() {
			public void run() {
				p.setScene(p.getServerSelect());
			}
		});
		
		// Initialisation des variables
		onresize(p);
		
		super.init(p);
	}
	
	public void draw(Main p) {
		Background.drawAmazingBackground(p);
		
		p.stroke(0);
		p.fill(255);
		
		// Dessin du texte
		p.textSize(titleTextSize);
		p.text(titleText, p.width / 2 - titleHalfWidth, p.height * 0.2f);
		
		
		// Des boutons
		playButton.draw(p);
		serverSelectButton.draw(p);
	}
	
	
	
	public void onresize(Main p) {
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
		
		
		// Taille des boutons
		playButton.setPosition(0.2f * p.width, 0.5f * p.height);
		serverSelectButton.setPosition(0.2f * p.width, 0.7f * p.height);
		
		playButton.setSize(0.6f * p.width, 0.15f * p.height);
		serverSelectButton.setSize(0.6f * p.width, 0.15f * p.height);
	}
	
	public void onclick(Main p) {
		playButton.onclick(p);
		serverSelectButton.onclick(p);
	}
	
	public void onmousemove(Main p) {
		playButton.onmousemove(p);
		serverSelectButton.onmousemove(p);
	}
}
