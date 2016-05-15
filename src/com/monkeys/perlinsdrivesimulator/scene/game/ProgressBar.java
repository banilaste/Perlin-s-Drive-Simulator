package com.monkeys.perlinsdrivesimulator.scene.game;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.scene.Scene;

import processing.core.PVector;

/**
 * Classe de gestion des barres de progression
 * @author Banilaste
 *
 */
public class ProgressBar extends Scene {
	private float level, max;
	private PVector pos, size;
	private int color, margin = 3;
	private String label = "{}";
	
	public ProgressBar(Main p) {
		super(p);
	}
	
	public void init(Main p) {
		pos = new PVector();
		size = new PVector();
	}
	
	/**
	 * Affichage de la barre de progression
	 */
	public void draw(Main p) {
		// Dessin du rectangle de fond
		p.fill(60);
		p.noStroke();
		
		p.rect(pos.x, pos.y, size.x, size.y);
		
		// Puis du rectangle principal
		p.fill(color);
		
		p.rect(pos.x + margin, pos.y + margin, (size.x - margin * 2) * level, size.y - margin * 2);
		
		// Et enfin du texte (on remplace {} par la valeur en % de la barre)
		String caption = label.replace("{}", "" + Math.round(level * 100));
		
		p.fill(255);
		p.textSize((size.y - margin * 2) * 0.6f);
		p.text(caption, pos.x + margin + size.x / 2 - p.textWidth(caption) / 2, pos.y + (size.y - margin * 2) * 0.8f);
	}
	
	/**
	 * Dessine le composant avec une matrice remise à 0
	 * @param p
	 */
	public void drawWithMatrix(Main p) {
		p.pushMatrix();
		p.resetMatrix();
		
		draw(p);
		
		p.popMatrix();
	}
	
	/*
	 * Getters / Setters
	 */
	public ProgressBar setValue(float value) {
		level = value / max; // Valeur relative (en %)
		return this;
	}
	
	public ProgressBar setMaxValue(float max) {
		this.max = max;
		return this;
	}
	
	public ProgressBar setLevel(float level) {
		this.level = level;
		return this;
	}
	
	public ProgressBar setColor(int color) {
		this.color = color;
		return this;
	}

	public ProgressBar setLabel(String label) {
		this.label = label;
		return this;
	}
	public PVector getSize() {
		return size;
	}

	public PVector getPosition() {
		return pos;
	}
}
