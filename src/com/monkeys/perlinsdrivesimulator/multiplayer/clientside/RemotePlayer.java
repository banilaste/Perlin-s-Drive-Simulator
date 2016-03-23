package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.Voiture;

import processing.core.PApplet;

public class RemotePlayer extends Voiture {
	protected int id;
	protected long lastUpdateTime, lastPingTime, currentTime;
	private RemoteConnection connection;
	
	public RemotePlayer(RemoteConnection connection, int id) {
		// La classe supérieure ne nécessite pas processing, on lui évite donc cet ennui
		// TODO: mettre à jour si la classe Voiture le requiert un jour
		super(null);
		
		this.connection = connection;
		this.id = id;
	}
	
	// La méthode ne sert plus qu'a vérifier la différence de temps depuis la dernière actualisation
	public void update(Main p) {
		currentTime = new Date().getTime();
		
		// Si le joueur n'est pas mis à jour depuis 5 secondes
		if (currentTime > lastUpdateTime + 5) {
			
			// Si un ping est envoyé, on attend 5 secondes avant le prochain
			if (currentTime > lastPingTime + 5) {
				// On envoie une demande de ping
				connection.send(RequestType.PING, id, "pls");
			
				lastPingTime = currentTime;
			}
		}
		
		// On met quand même la mise à jour de la position en fonction de la vitesse (en cas
		// de coupure ca risque d'être drôle)
		this.position.add(this.speed);
	}
	
	// Gère la requète recue du serveur
	public RemotePlayer request(int reqType, String data) {
		if (reqType == RequestType.PONG_ALIVE.id) {
			
			
		} else if (reqType == RequestType.PONG_DEAD.id) {
			
			// On se supprime simplement de la liste si le joueur associé est déconnecté
			connection.getPlayers().remove(id);
			
		} else if (reqType == RequestType.POSITION.id) {
			// Si c'est une MaJ de position, onécontent

			String parts[] = data.split(" ");
			
			this.position.x = Float.parseFloat(parts[0]);
			this.position.y = Float.parseFloat(parts[1]);
			this.speed.x = Float.parseFloat(parts[2]);
			this.speed.y = Float.parseFloat(parts[3]);
		}
		
		// On met à jour le temps de la dernière mise à jour
		lastUpdateTime = currentTime;
		
		return this;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
