package com.evsward.event_aggregator;

public enum Event {
	STARK_SIGHTED("Stark sighted"), KINGS_HAND("king's hand"), WARSHIPS_APPROACHING(
			"Warships approaching"), TRAITOR_DETECTED("Traitor detected");
	private String description;

	Event(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}
}
