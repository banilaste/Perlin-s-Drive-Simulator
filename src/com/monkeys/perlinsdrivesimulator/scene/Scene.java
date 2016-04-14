package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;

public class Scene {
	protected boolean initialized = false;
	
	public Scene() {}
	
	public Scene(Main p) {
		init(p);
	}
	
	public void init(Main p) {
		initialized = true;
	}
	
	public void draw(Main p) {}
	public void onresize(Main p) {}
	public void onclick(Main p) {}
	public void onmousemove(Main p) {}
	public void onkeytyped(Main p) {}
}
