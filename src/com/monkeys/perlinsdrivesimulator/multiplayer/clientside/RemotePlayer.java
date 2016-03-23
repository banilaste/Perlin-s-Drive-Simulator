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
		// La classe sup�rieure ne n�cessite pas processing, on lui �vite donc cet ennui
		// TODO: mettre � jour si la classe Voiture le requiert un jour
		super(null);
		
		this.connection = connection;
		this.id = id;
	}
	
	// La m�thode ne sert plus qu'a v�rifier la diff�rence de temps depuis la derni�re actualisation
	public void update(Main p) {
		currentTime = new Date().getTime();
		
		// Si le joueur n'est pas mis � jour depuis 5 secondes
		if (currentTime > lastUpdateTime + 5) {
			
			// Si un ping est envoy�, on attend 5 secondes avant le prochain
			if (currentTime > lastPingTime + 5) {
				// On envoie une demande de ping
				connection.send(RequestType.PING, id, "pls");
			
				lastPingTime = currentTime;
			}
		}
		
		// On met quand m�me la mise � jour de la position en fonction de la vitesse (en cas
		// de coupure ca risque d'�tre dr�le)
		this.position.add(this.speed);
	}
	
	// G�re la requ�te recue du serveur
	public RemotePlayer request(int reqType, String data) {
		if (reqType == RequestType.PONG_ALIVE.id) {
			
			
		} else if (reqType == RequestType.PONG_DEAD.id) {
			
			// On se supprime simplement de la liste si le joueur associ� est d�connect�
			connection.getPlayers().remove(id);
			
		} else if (reqType == RequestType.POSITION.id) {
			// Si c'est une MaJ de position, on�content

			String parts[] = data.split(" ");
			
			this.position.x = Float.parseFloat(parts[0]);
			this.position.y = Float.parseFloat(parts[1]);
			this.speed.x = Float.parseFloat(parts[2]);
			this.speed.y = Float.parseFloat(parts[3]);
		}
		
		// On met � jour le temps de la derni�re mise � jour
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
