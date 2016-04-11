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

public class RemoteConnection implements Runnable {
	private Map<Integer, RemotePlayer> players;
	private String username, ip;
	private int port;
	
	private Socket soc;
	private Thread thread;
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	private Main main;
	
	// Initialisation d'un nouveau client
	public RemoteConnection (String ip, int port, String name, Main m) throws IOException {
		this.ip = ip;
		this.port = port;
		
		main = m;
		username = name;
		
		// Création du thread et démarage
		thread = new Thread(this);
		thread.start();
	}
	
	// Fonction du processuss
	public void run() {
		String nextLine, data;
		int playerId, startData, reqType;
		
		try {
			// Initialisation
			init();
			
			while(true) {
				nextLine = reader.readLine();

				// Le premier caractère représente le nombre de caractère de l'id (1 pour un id entre 0 et 9...)
				startData = Character.getNumericValue(nextLine.charAt(0)) + 1;
				playerId = Integer.parseInt(nextLine.substring(1, startData));
				
				// Le caractère suivant représente le type de requète
				reqType = Character.getNumericValue(nextLine.charAt(startData));
				
				// Puis viennent les données
				data = nextLine.substring(startData + 1);
				
				// On envoie ensuite à l'instance locale concernée
				if (reqType == RequestType.SEED.id) {
					main.noiseSeed(Long.parseLong(data));
					main.getGame().getGround().generate(main, main.getGame().getGround().getSection(), false);
					
				} else if (players.containsKey(playerId)) {
					players.get(playerId).request(reqType, data);
					
				} else {
					// Request() renvoie l'objet que l'on vient de créer (pas de soucis à se faire ;D)
					players.put(playerId, new RemotePlayer(this, playerId).request(reqType, data));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void init() throws UnknownHostException, IOException {
		soc = new Socket(ip, port);
		
		players = new HashMap<Integer, RemotePlayer>();
		
		// Création des objets de lecture/écriture
		reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		writer = new PrintWriter(soc.getOutputStream());
	}
	
	/**
	 * Envoie une requète au serveur (pour envoyer ou demander des infos)
	 * Note : les requète PONG_* ne peuvent être envoyées
	 */
	public void send(RequestType type, int destinationId, String data) {
		
		// Envoi du type de requète sous forme de chaine
		writer.write(type.id + "");
		
		if (type == RequestType.PING) { // Demande de "ping"
			
			// longueur id + id
			writer.write(Math.round(Math.log10(destinationId) + 1) + "" + destinationId);
		
		} else if (type == RequestType.POSITION) {
			writer.write(data);
		}
		
		// Retour à la ligne pour signifier la fin du contenu
		writer.write("\n");
		writer.flush();
	}
	
	public Map<Integer, RemotePlayer> getPlayers() {
		return players;
	}
}