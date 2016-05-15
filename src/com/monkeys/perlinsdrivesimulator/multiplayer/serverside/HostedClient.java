package com.monkeys.perlinsdrivesimulator.multiplayer.serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.monkeys.perlinsdrivesimulator.multiplayer.clientside.RequestType;

/**
 * Classe de gestion d'un client du serveur
 * @author Banilaste
 *
 */
public class HostedClient implements Runnable {
	private static HashMap<Integer, HostedClient> clients = new HashMap<Integer, HostedClient>();
	private static int currentId = 0;
	private static long seed = (long) Math.random();
	
	private String username;
	private Socket soc;
	private Thread thread;
	
	private BufferedReader reader;
	private PrintWriter writer;
	private int id;
	
	/**
	 * Initialisation d'un nouveau client
	 * @param soc Connection active
	 * @throws IOException
	 */
	public HostedClient (Socket soc) throws IOException {
		this.soc = soc;
		
		// Création des objets de lecture/écriture
		reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		writer = new PrintWriter(soc.getOutputStream());
		
		// Création du thread et démarage
		thread = new Thread(this);
		thread.start();
		
		// Définition d'un id et ajout à la liste
		id = ++currentId;
		clients.put(id, this);
	}

	/**
	 * Fonctionnement du thread
	 */
	public void run() {
		// Envoie de la seed au client
		this.sendFrom(id, RequestType.SEED.getRequest("" + seed));
		
		while (true) {
			try {
				// Récupération d'une ligne (= 1 requête)
				String data = reader.readLine();
				RequestType type = RequestType.getFromString(data);
				boolean cancelBroadcast = false;
				
				// Debug (affichage des requêtes
				if (type != RequestType.POSITION) {
					System.out.println("Received from " + id + ": " + data.substring(1) + " (" + type.toString() + ")");
				}
				
				// Si c'est un envoi de nom d'utilisateur, on l'intercepte pour le le sauvegarder avant envoi
				if (type == RequestType.I_AM) {
					username = data.substring(1);
				
				} else if (type == RequestType.PING) { // Si c'est un ping
					int id = Integer.parseInt(data.substring(1));
					
					// On envoie l'état du client selon son état de connection
					if (clients.containsKey(id) && clients.get(id).isAlive()) {
						sendFrom(id, RequestType.PONG_ALIVE.getRequest(id + ""));
					} else {
						sendFrom(id, RequestType.PONG_DEAD.getRequest(id + ""));
					}
					
					cancelBroadcast = true;
				
				// Si c'est une demande de nom
				} else if (type == RequestType.WHO_IS) {
					int userId = Integer.parseInt(data.substring(1));
					
					// Si le client a déja indiqué son nom, on n'envoie pas de broadcast
					if (clients.containsKey(userId) && clients.get(userId).getUsername() != null) {
						// Envoi du nom d'utilisateur
						sendFrom(userId, clients.get(userId).getUsername());
						cancelBroadcast = true;
					}
				}
				
				// Fonctionnement en broadcast (envoi à tout le monde sauf la source)
				if (!cancelBroadcast) {
					sendBroadcast(data);
				}
				
			} catch (IOException e) {
				// Envoi d'un pong de déconnection aux autres clients
				System.err.println("IO error with client " + id + ", disconnecting.");
				sendBroadcast(RequestType.PONG_DEAD.id + "");
				disconnect();
				
				// Suppression de la liste
				clients.remove(id);
				break;
			}
		}
		
		clients.remove(this);
	}
	
	/**
	 * Envoi un message à tous les autres clients
	 * @param message
	 */
	private void sendBroadcast(String message) {
		for (HostedClient client : clients.values()) {
			// On envoie le nombre de lettre que prend l'id (log10), l'id, et la donnée transmise
			// log10 suffisant pour tout id de type int
			if (client != this) {
				client.sendFrom(id, message);
			}
		}
	}
	
	/**
	 * Ferme les flux d'entrée/sortie
	 */
	public void disconnect() {
		try {
			writer.close();
			reader.close();
			soc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envoi d'un message au client depuis un autre client spécifié
	 * @param id ID de la source
	 * @param data Message
	 */
	public void sendFrom(int id, String data) {
		// Si aucune fin de ligne n'est spécifiée
		if (data.lastIndexOf("\n") != data.length() - 1) {
			data += "\n";
		}
		
		writer.write((int) Math.floor(Math.log10(id) + 1) + "" + id + data);
		writer.flush();
	}
	
	/*
	 * Getters / Setters
	 */
	public boolean isAlive() {
		return soc.isConnected();
	}
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
}
