package com.monkeys.perlinsdrivesimulator.multiplayer.clientside;

public enum RequestType {
	PING, PONG_ALIVE, PONG_DEAD, POSITION;

	final int id;

	RequestType() {
		id = ordinal();
	}

	static RequestType getFromNumber(int nb) {
		try {
			return RequestType.values()[nb];
		} catch ( ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Unknown request type with id : " + nb);
		}
	}
}
