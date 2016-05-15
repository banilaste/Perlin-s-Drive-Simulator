package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.Callback;

/**
 * Scène du menu principal
 */
public class MainMenuScene extends Scene {
	protected int titleTextSize, titleHalfWidth;
	protected String titleText;
	private Button playButton, serverSelectButton;
	
	public MainMenuScene(Main main) {
		super(main);
	}

	public void init(Main p) {
		// Titre
		titleText = "Perlin's Drive Simulator";
		
		// Création des boutons et ajout de leurs fonctions d'action
		playButton = new Button(p, "Play !", new Callback() {
			public void run() {
				// Initialisation du jeu et changement de scène
				p.getGame().init(p);
				p.setScene(p.getGame());
			}
		});

		serverSelectButton = new Button(p, "Multiplayer !", new Callback() {
			public void run() {
				// On met la scène de sélection de serveur en scène active
				p.setScene(p.getServerSelect());
			}
		});
		
		// Initialisation des variables
		onresize(p);
		
		super.init(p);
	}
	
	/**
	 * Affichage
	 */
	public void draw(Main p) {
		// Fond d'écran
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
	
	
	/**
	 * Gestion du redimensionnement
	 */
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
		
		
		// Taille et position des boutons
		playButton.setPosition(0.2f * p.width, 0.5f * p.height);
		serverSelectButton.setPosition(0.2f * p.width, 0.7f * p.height);
		
		// 60% de largeur, 15% de hauteur
		playButton.setSize(0.6f * p.width, 0.15f * p.height);
		serverSelectButton.setSize(0.6f * p.width, 0.15f * p.height);
	}
	
	public void onclick(Main p) {
		// Transmission aux composants
		playButton.onclick(p);
		serverSelectButton.onclick(p);
	}
	
	public void onmousemove(Main p) {
		// Transmission aux composants
		playButton.onmousemove(p);
		serverSelectButton.onmousemove(p);
	}
}
