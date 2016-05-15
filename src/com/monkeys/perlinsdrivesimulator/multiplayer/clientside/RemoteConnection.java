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
		
		// Cr�ation du thread et d�marage
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Boucle de r�ception
	 */
	public void run() {
		String nextLine, data;
		int playerId, startData, reqType;
		
		try {
			// Initialisation
			init();
			
			while(true) {
				// R�cup�ration d'une ligne (= 1 requ�te)
				nextLine = reader.readLine();

				// Le premier caract�re repr�sente le nombre de caract�re de l'id (1 pour un id entre 0 et 9...)
				startData = Character.getNumericValue(nextLine.charAt(0)) + 1;
				playerId = Integer.parseInt(nextLine.substring(1, startData));
				
				// Le caract�re suivant repr�sente le type de requ�te
				reqType = Character.getNumericValue(nextLine.charAt(startData));
				
				// Puis viennent les donn�es
				data = nextLine.substring(startData + 1);
				
				// On envoie ensuite � l'instance locale concern�e
				if (reqType == RequestType.SEED.id) { // Envoi de SEED
					main.noiseSeed(Long.parseLong(data));
					main.getGame().getGround().generate(main, main.getGame().getGround().getSection(), false);
					
				} else if (reqType == RequestType.WHO_IS.id) { // Demande de nom
					send(RequestType.I_AM, username);
					
				} else if (players.containsKey(playerId)) { // Gestion sp�cifique d'un joueur d�j� existant
					players.get(playerId).request(reqType, data);
					
				} else { // Gestion sp�cifique d'un joueur non existant
					// Ajout du nouveau joueur � la liste
					players.put(playerId, new RemotePlayer(this, playerId).request(reqType, data));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialise les flux d'entr�e/sortie
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void init() throws UnknownHostException, IOException {
		soc = new Socket(ip, port);
		
		players = new HashMap<Integer, RemotePlayer>();
		
		// Cr�ation des objets de lecture/�criture
		reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		writer = new PrintWriter(soc.getOutputStream());
	}
	
	/**
	 * Envoie une requ�te au serveur (pour envoyer ou demander des infos)
	 * Note : les requ�te PONG_* ne devraient pas �tre envoy�es
	 */
	public void send(RequestType type, String data) {
		
		// Envoi du type de requ�te sous forme de chaine
		writer.write(type.id + "");
		
		writer.write(data);
		
		// Retour � la ligne pour signifier la fin du contenu
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