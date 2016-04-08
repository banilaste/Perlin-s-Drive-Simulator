package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.Callback;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Button extends Scene {
	private Callback callback;
	private PVector position, size;
	private String text;
	private int textSize;
	private boolean focused = false;
	
	public Button(Main p, String text, Callback callback) {
		super(p);
		
		this.text = text;
		this.callback = callback;
	}
	
	public void init(Main p) {
		position = new PVector();
		size = new PVector();
	}
	
	public void draw(Main p) {
		// Rectangle
		p.stroke(10);
		p.strokeWeight(4);
		
		if (!focused) {
			p.fill(0);
		} else {
			p.fill(150, 40, 40);
		}
		
		p.rect(position.x, position.y, size.x, size.y, size.x * 0.03f);
		
		// Texte
		p.fill(255);
		
		p.textSize(textSize);
		p.text(text, position.x + (size.x - p.textWidth(text)) / 2, position.y + size.y * 0.7f);
	}
	
	public void onclick(Main p) {
		if (!isIn(p.mouseX, p.mouseY))
			return;
		
		callback.run();
	}
	
	public void onmousemove(Main p) {
		if (isIn(p.mouseX, p.mouseY)) {
			focused = true;
			p.cursor(PConstants.HAND);
			
		// On ne change qu'une fois l'état du curseur pour éviter des conflits
		} else if(focused) {
			p.cursor(PConstants.ARROW);
			focused = false;
		}
	}
	
	public boolean isIn(int x, int y) {
		// Vérificaition du clic pour x
		if (x < position.x || x > position.x + size.x)
			return false;
		
		// Puis pour y
		if (y < position.y || y > position.y + size.y)
			return false;
		
		return true;
	}
	
	// Setters
	public void setSize(int width, int height) {
		size.set(width, height);
	}
	
	/**
	 * Défini la largeur du bouton et, si demandé, la hauteur pour que le texte rentre dans la case
	 * @param width
	 * @param scaleHeight
	 * @param p
	 */
	public void setWidth(int width, boolean scaleHeight, PApplet p) {
		size.x = width;
		
		// Modification de la hauteur et de la taille du texte
		// pour que le texte rentre dans la case
		if (scaleHeight) {
			p.textSize(20);
			float requiredWidth = p.textWidth(text);
			
			size.y = 20 * size.x / requiredWidth;
			textSize = (int) (size.y * 0.6f);
		}
	}
	
	public void setPosition(int x, int y) {
		position.set(x, y);
	}
}
