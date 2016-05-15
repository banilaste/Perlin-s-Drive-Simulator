package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

import java.util.Date;

import com.monkeys.perlinsdrivesimulator.Main;
import com.monkeys.perlinsdrivesimulator.scene.game.Player;

/**
 * Classe de gestion d'un joueur distant
 * @author Banilaste
 *
 */
public class RemotePlayer extends Player {
	protected int id;
	protected long lastUpdateTime, lastPingTime, currentTime;
	protected boolean usernameRequested = false;
	private RemoteConnection connection;
	
	public RemotePlayer(RemoteConnection connection, int id) {
		// La classe supérieure ne nécessite pas processing, on lui évite donc cet ennui
		// TODO: mettre à jour si la classe Player le requiert un jour
		super(null);
		
		this.lastUpdateTime = new Date().getTime() + 5000; // on laisse 10s avant le premier ping
		this.username = "unnamed";
		this.connection = connection;
		this.id = id;
	}
	
	/**
	 * Mise à jour du temps depuis la dernière mise à jour,
	 * envoie de PING ou de WHO_IS si besoin est.
	 */
	public void update(Main p) {
		currentTime = new Date().getTime();
		
		// Si le joueur n'est pas mis à jour depuis 5 secondes
		if (currentTime > lastUpdateTime + 5000) {
			
			// Si un ping est envoyé, on attend 5 secondes avant le prochain
			if (currentTime > lastPingTime + 5000) {
				// On envoie une demande de ping
				connection.send(RequestType.PING, "" + id);
			
				lastPingTime = currentTime;
			}
		}
		
		// Si on n'a jamais demandé le nom d'utilisateur, il est surement temps
		if (!usernameRequested) {
			connection.send(RequestType.WHO_IS, "" + id);
			usernameRequested = true;
		}
		
		// On met quand même la mise à jour de la position en fonction de la vitesse (en cas
		// de coupure ca risque d'être drôle)
		this.position.add(this.speed);
		this.angle += rotationSpeed;
	}
	
	/**
	 * Gère la requète recue du serveur
	 * @param reqType Type de la requête
	 * @param data Données jointes
	 * @return
	 */
	public RemotePlayer request(int reqType, String data) {
		if (reqType == RequestType.PONG_DEAD.id) { // Le joueur associé est déconnecté
			
			// On se supprime simplement de la liste si le joueur associé est déconnecté
			connection.getPlayers().remove(id);
			
		} else if (reqType == RequestType.POSITION.id) { // Mise à jour de position
			String parts[] = data.split(" ");
			
			// Récupération des données
			this.position.x = Float.parseFloat(parts[0]);
			this.position.y = Float.parseFloat(parts[1]);
			this.speed.x = Float.parseFloat(parts[2]);
			this.speed.y = Float.parseFloat(parts[3]);
			this.angle = Float.parseFloat(parts[4]);
			this.rotationSpeed = Float.parseFloat(parts[5]);
		
		} else if (reqType == RequestType.I_AM.id) { // Envoi du nom
			username = data;
		}
		
		// On met à jour le temps de la dernière mise à jour
		lastUpdateTime = currentTime;
		
		return this;
	}
}
