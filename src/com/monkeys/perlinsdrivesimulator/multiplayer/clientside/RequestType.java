package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

public enum RequestType {
	PING, PONG_ALIVE, PONG_DEAD, POSITION, SEED, WHO_IS, I_AM;

	public final int id;

	RequestType() {
		id = ordinal();
	}

	public static RequestType getFromNumber(int nb) {
		try {
			return RequestType.values()[nb];
		} catch ( ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Unknown request type with id : " + nb);
		}
	}
	
	public static RequestType getFromString(String c) {
		try {
			return RequestType.values()[Integer.parseInt(c.substring(0, 1))];
		} catch ( ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Unknown request type with id : " + c);
		}
	}
	
	public String getRequest(String data) {
		return this.id + data + "\n";
	}
}
