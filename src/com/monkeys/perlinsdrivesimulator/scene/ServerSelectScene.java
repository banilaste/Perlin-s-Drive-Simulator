package com.monkeys.perlinsdrivesimulator.scene;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.container.Callback;

public class ServerSelectScene extends Scene {
	private Button mainMenu, accept;
	private TextBox serverName, username;
	private NumberBox serverPort;
	
	public ServerSelectScene(Main main) {
		super(main);
	}

	public void init(Main p) {
		mainMenu = new Button(p, "Go back", new Callback() {
			public void run() {
				p.setScene(p.getMainMenu());
			}
		});
		
		accept = new Button(p, "Let's go !", new Callback() {
			public void run() {
				// On change la scène après être connecté pour être sur de ne pas planter
				p.getGame().enableMultiplayer(p, serverName.getText(), serverPort.getInteger(), username.getText());
				p.getGame().getPlayer().setName(username.getText());
				
				p.setScene(p.getGame());
			}
		});

		username = new TextBox(p, "Username");
		serverName = new TextBox(p, "Server address");
		serverPort = new NumberBox(p, "Server port");
		
		username.nextElement = serverName;
		serverName.nextElement = serverPort;
		serverPort.nextElement = username;
		
		// TODO: remove
		serverName.setText("127.0.0.1");
		serverPort.setText("12345");
		
		onresize(p);
	}
	
	public void draw(Main p) {
		Background.drawAmazingBackground(p);
		
		username.draw(p);
		serverName.draw(p);
		serverPort.draw(p);
		
		accept.draw(p);
		mainMenu.draw(p);
	}
	
	public void onkeytyped(Main p) {
		serverName.onkeytyped(p);
		username.onkeytyped(p);
		serverPort.onkeytyped(p);
	}

	public void onresize(Main p) {
		// Redimensionnement des zones de textes et des boutons
		username.setSize(p.width * 0.6f, p.height * 0.1f);
		serverName.setSize(p.width * 0.6f, p.height * 0.1f);
		serverPort.setSize(p.width * 0.6f, p.height * 0.1f);
		
		username.setPosition(p.width * 0.2f, p.height * 0.1f);
		serverName.setPosition(p.width * 0.2f, p.height * 0.3f);
		serverPort.setPosition(p.width * 0.2f, p.height * 0.5f);
		
		// Puis des boutons
		accept.setSize(p.width * 0.28f, p.height * 0.1f);
		mainMenu.setSize(p.width * 0.28f, p.height * 0.1f);
		
		mainMenu.setPosition(p.width * 0.2f, p.height * 0.8f);
		accept.setPosition(p.width * 0.52f, p.height * 0.8f);
	}
	
	public void onclick(Main p) {
		mainMenu.onclick(p);
		accept.onclick(p);
		
		username.onclick(p);
		serverName.onclick(p);
		serverPort.onclick(p);
	}
	
	public void onmousemove(Main p) {
		mainMenu.onmousemove(p);
		accept.onmousemove(p);
		
		username.onmousemove(p);
		serverName.onmousemove(p);
		serverPort.onmousemove(p);
	}
}
