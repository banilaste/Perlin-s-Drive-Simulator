package com.monkeys.perlinsdrivesimulator.multiplayer.serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class HostedClient implements Runnable {
	private static ArrayList<HostedClient> clients = new ArrayList<HostedClient>();
	private static int currentId = 0;
	
	private HostServer server;
	private Socket soc;
	private Thread thread;
	
	private BufferedReader reader;
	private PrintWriter writer;
	private int id;
	
	// Initialisation d'un nouveau client
	public HostedClient (Socket soc, HostServer server) throws IOException {
		this.server = server;
		this.soc = soc;
		
		// Création des objets de lecture/écriture
		reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		writer = new PrintWriter(soc.getOutputStream());
		
		// Création du thread et démarage
		thread = new Thread(this);
		thread.start();
		
		// Définition d'un id et ajout à la liste
		id = ++currentId;
		clients.add(this);
	}

	// Fonctionnement du thread
	public void run() {
		
		while (true) {
			try {
				String data = reader.readLine();

				// Fonctionnement en broadcast (envoi à tout le monde)
				for (HostedClient client : clients) {
					// On envoie le nombre de lettre que prend l'id (log10), l'id, et la donnée transmise
					// log10 suffisant pour tout id de type int
					if (client != this) {
						client.send((int) Math.floor(Math.log10(id) + 1) + "" + id + data + "\n");
					}
				}
			} catch (IOException e) {
				System.err.println("IO error with client " + id + ", disconnecting.");
				disconnect();
				break;
			}
		}
		
		clients.remove(this);
	}
	
	
	
	public void disconnect() {
		try {
			writer.close();
			reader.close();
			soc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String data) {
		writer.write(data);
		writer.flush();
	}
	
	public int getId() {
		return id;
	}
}
