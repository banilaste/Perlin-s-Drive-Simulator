package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

import processing.core.PApplet;

public class Scene {
	protected boolean initialized = false;
	
	public void init(Main p) {
		initialized = true;
	}
	
	public void draw(Main p) {
		p.text("Default scene :D", 0, 10);
	}
	
	public void onresize(Main p) {}
	public void onclick(Main p) {}
}
