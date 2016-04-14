package com.monkeys.perlinsdrivesimulator.multiplayer.serverside;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HostServer implements Runnable {
	private ServerSocket server;
	private Thread thread;
	private boolean stop = false;
	private int port;
	
	public HostServer(int port) {
		this.port = port;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		Socket next;
		HostedClient client;
		
		try {
			
			// Init
			server = new ServerSocket(port);
			
			System.out.println("Starting server on port " + port);
			
			// Loop
			while (! stop) {
				next = server.accept();
				client = new HostedClient(next, this);
				
				System.out.println("New client from " + next.getInetAddress().getHostAddress() + ", using id " + client.getId());
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void stop() {
		stop = true;
	}
	
	public static void main(String[] args) {
		HostServer server = new HostServer(12345);
	}
}
