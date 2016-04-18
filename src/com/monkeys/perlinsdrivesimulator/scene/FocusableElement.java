package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PConstants;

public class FocusableElement extends Scene {
	public static FocusableElement currentFocus;
	protected static int mouseDefaultCursor = PConstants.ARROW;
	
	protected int mouseFocusCursor = PConstants.HAND;
	protected boolean hasMouseFocus, hasFocus;
	protected FocusableElement nextElement, previousElement;
	
	public FocusableElement(Main p) {
		super(p);
	}

	public boolean isPointIn(float x, float y) {
		return true;
	}
	
	public void onmousemove(Main p) {
		if (isPointIn(p.mouseX, p.mouseY)) {
			hasMouseFocus = true;
			p.cursor(mouseFocusCursor);
			
		} else if (hasMouseFocus) {
			p.cursor(mouseDefaultCursor);
			hasMouseFocus = false;
		}
	}
	
	public void onclick(Main p) {
		if (isPointIn(p.mouseX, p.mouseY)) {
			focus();
		
		} else if (hasFocus) {
			unfocus();
		}
	}
	
	public void focus() {
		hasFocus = true;
		
		if (currentFocus != null && currentFocus != this) {
			currentFocus.unfocus();
		}
		
		currentFocus = this;
	}
	
	public void unfocus() {
		hasFocus = false;
	}
	
	public void focusNext() {
		if (nextElement != null && hasFocus) {
			nextElement.focus();
		}
	}
	
	public void focusPrevious() {
		if (previousElement != null && hasFocus) {
			previousElement.focus();
		}
	}
}
