package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;

public class NumberBox extends TextBox {

	public NumberBox(Main p, String text) {
		super(p, text);
	}
	
	public void onkeytyped(Main p) {
		if (!editing) return;
		
		if (p.keyCode == PConstants.BACKSPACE) {
			if (text.length() == 0) return;
			
			text = text.substring(0, text.length() - 1);
			return;
		
		} else if (p.key >= '0' && p.key <= '9') {
			text += p.key;
		}
	}
	
	public int getInteger() {
		if (text.length() == 0) return 0;
		
		return Integer.parseInt(getText());
	}

}
