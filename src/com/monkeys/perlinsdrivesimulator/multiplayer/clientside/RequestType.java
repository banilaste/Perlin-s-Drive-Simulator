package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

/**
 * Liste des types de requ�te
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
	 * R�cup�re le type de requ�te associ� � la requ�te donn�e
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
	 * Renvoie la requ�te associ�e aux donn�es
	 * @param data Donn�es � envoyer avec la requ�te
	 * @return
	 */
	public String getRequest(String data) {
		return this.id + data + "\n";
	}
}
