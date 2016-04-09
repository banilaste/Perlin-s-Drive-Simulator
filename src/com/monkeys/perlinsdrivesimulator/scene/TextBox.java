package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;
import processing.core.PVector;

public class TextBox extends Scene {
	protected PVector position, size;
	protected String text = "", emptyText = "Enter somthing :D";
	protected int textSize, focusBlinkDelay;
	protected boolean focused = false, editing = false;
	
	public TextBox(Main p, String text) {
		super(p);
		
		this.emptyText = text;
	}
	
	public void init(Main p) {
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
		if (editing) {
			focusBlinkDelay --;
			
			if (focusBlinkDelay <= 0) {
				focusBlinkDelay = 70;
			} else if (focusBlinkDelay <= 35) {
				p.rect(position.x + p.textWidth(text) + 10, position.y + size.y * 0.2f, 2, textSize);
			}
			
			
		}
	}
	
	public void onclick(Main p) {
		if (isIn(p.mouseX, p.mouseY)) {
			editing = true;
			focusBlinkDelay = 35;
		} else {
			editing = false;
		}
	}
	
	public void onkeytyped(Main p) {
		if (!editing) return;
		
		if (p.keyCode == PConstants.BACKSPACE) {
			if (text.length() == 0) return;
			
			text = text.substring(0, text.length() - 1);
			return;
		} else if (p.key != PConstants.CODED) {
			System.out.println("ok");
			text += p.key;
		}
	}
	
	public void onmousemove(Main p) {
		if (isIn(p.mouseX, p.mouseY)) {
			focused = true;
			p.cursor(PConstants.TEXT);
			
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
}
