package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.monkeys.perlinsdrivesimulator.Main;

/**
 * Classe de gestion de connection distante
 * @author Banilaste
 *
 */
public class RemoteConnection implements Runnable {
	private Map<Integer, RemotePlayer> players;
	private String username, ip;
	private int port;
	
	private Socket soc;
	private Thread thread;
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	private Main main;
	
	/**
	 * Initialisation d'un nouveau client
	 * @param ip IP du serveur
	 * @param port Port du serveur
	 * @param name Nom d'utilisateur
	 * @param m
	 * @throws IOException
	 */
	public RemoteConnection (String ip, int port, String name, Main m) throws IOException {
		this.ip = ip;
		this.port = port;
		
		main = m;
		username = name;
		
		// Création du thread et démarage
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Boucle de réception
	 */
	public void run() {
		String nextLine, data;
		int playerId, startData, reqType;
		
		try {
			// Initialisation
			init();
			
			while(true) {
				// Récupération d'une ligne (= 1 requête)
				nextLine = reader.readLine();

				// Le premier caractère représente le nombre de caractère de l'id (1 pour un id entre 0 et 9...)
				startData = Character.getNumericValue(nextLine.charAt(0)) + 1;
				playerId = Integer.parseInt(nextLine.substring(1, startData));
				
				// Le caractère suivant représente le type de requète
				reqType = Character.getNumericValue(nextLine.charAt(startData));
				
				// Puis viennent les données
				data = nextLine.substring(startData + 1);
				
				// On envoie ensuite à l'instance locale concernée
				if (reqType == RequestType.SEED.id) { // Envoi de SEED
					main.noiseSeed(Long.parseLong(data));
					main.getGame().getGround().generate(main, main.getGame().getGround().getSection(), false);
					
				} else if (reqType == RequestType.WHO_IS.id) { // Demande de nom
					send(RequestType.I_AM, username);
					
				} else if (players.containsKey(playerId)) { // Gestion spécifique d'un joueur déjà existant
					players.get(playerId).request(reqType, data);
					
				} else { // Gestion spécifique d'un joueur non existant
					// Ajout du nouveau joueur à la liste
					players.put(playerId, new RemotePlayer(this, playerId).request(reqType, data));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialise les flux d'entrée/sortie
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void init() throws UnknownHostException, IOException {
		soc = new Socket(ip, port);
		
		players = new HashMap<Integer, RemotePlayer>();
		
		// Création des objets de lecture/écriture
		reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		writer = new PrintWriter(soc.getOutputStream());
	}
	
	/**
	 * Envoie une requète au serveur (pour envoyer ou demander des infos)
	 * Note : les requète PONG_* ne devraient pas être envoyées
	 */
	public void send(RequestType type, String data) {
		
		// Envoi du type de requète sous forme de chaine
		writer.write(type.id + "");
		
		writer.write(data);
		
		// Retour à la ligne pour signifier la fin du contenu
		writer.write("\n");
		writer.flush();
	}
	
	/*
	 * Getters / Setters
	 */
	public Map<Integer, RemotePlayer> getPlayers() {
		return players;
	}
}