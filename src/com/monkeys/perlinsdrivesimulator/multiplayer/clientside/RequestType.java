package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

/**
 * Liste des types de requête
 * @author Banilaste
 *
 */
public enum RequestType {
	PING, PONG_ALIVE, PONG_DEAD, POSITION, SEED, WHO_IS, I_AM;

	public final int id;

	RequestType() {
		id = ordinal();
	}
	
	/**
	 * Récupère le type de requête associé à la requête donnée
	 * @param c
	 * @return
	 */
	public static RequestType getFromString(String c) {
		try {
			return RequestType.values()[Integer.parseInt(c.substring(0, 1))];
		} catch ( ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Unknown request type with id : " + c);
		}
	}
	
	/**
	 * Renvoie la requête associée aux données
	 * @param data Données à envoyer avec la requête
	 * @return
	 */
	public String getRequest(String data) {
		return this.id + data + "\n";
	}
}
