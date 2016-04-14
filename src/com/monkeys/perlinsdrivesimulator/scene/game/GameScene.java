package com.monkeys.perlinsdrivesimulator.scene.game;

import java.io.IOException;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemoteConnection;
import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RemotePlayer;
import com.monkeys.perlinsdrivesimulator.scene.Scene;

public class GameScene extends Scene {
	private Ground ground;
	private Player player;
	private RemoteConnection multiplayer;
	
	protected ProgressBar fuelBar;
	
	private boolean multiplayerEnabled = false;
	
	public GameScene(Main main) {
		super(main);
	}

	public void init(Main p) {
		if (initialized) {
			return;
		}
		
		ground = new Ground(p);
		player = new Player(p);
		
		fuelBar = new ProgressBar(p)
			.setColor(p.color(150, 0, 0))
			.setMaxValue(1)
			.setLabel("Fuel : {} %");
		
		fuelBar.getPosition().set(10, 10);
		fuelBar.getSize().set(300, 30);
		
		super.init(p);
	}
	
	public void draw(Main p) {
		p.background(0);
		
		// Dessin des barres d'info
		fuelBar.draw(p);
		
		
		p.translate(-player.getPosition().x + p.width * 3/10, -player.getPosition().y + p.height / 2);
		
		// Mise à jour du joueur
		player.update(p);
		fuelBar.setLevel(player.getFuelLevel());
		
		// Dessin du joueur local
		player.draw(p);

		// Si le mode multijoueur est activé, on dessine aussi les voiture des autres
		// et on les met à jour
		if (multiplayerEnabled) {
			// On dessine tous les joueurs qu'importe leurs id
			for (RemotePlayer next : multiplayer.getPlayers().values()) {
				next.update(p);
				next.draw(p);
			}
		}
		
		// Dessin du sol
		ground.draw(p);
	}
	
	public void onresize(Main p) {
		ground.onresize(p);
	}
	
	/**
	 * Active le mode multijoueur en se connectant à un serveur
	 * @param p
	 * @param ip Adresse du serveur
	 * @param port Port sur le serveur
	 * @param username Nom d'utilisateur
	 */
	public void enableMultiplayer(Main p, String ip, int port, String username) {
		if (multiplayerEnabled) return;
		
		try {
			multiplayer = new RemoteConnection(ip, port, username, p);
		} catch (IOException e) {
			e.printStackTrace();
			multiplayerEnabled = false;
			return;
		}

		player.enableMultiplayerUpdate();
		multiplayerEnabled = true;
	}
	
	public void disableMultiplayer() {
		if (!multiplayerEnabled) return;
		
		// TODO: supression des objets instanciés
		multiplayerEnabled = false;
	}
	
	
	/*
	 * Getters
	 */
	public RemoteConnection getMultiplayerInstance() {
		return multiplayer;
	}

	public Ground getGround() {
		return ground;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isMultiplayerEnabled() {
		return multiplayerEnabled;
	}

	
}
