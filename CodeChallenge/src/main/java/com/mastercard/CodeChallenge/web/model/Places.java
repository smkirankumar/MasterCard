/*
 * Simple POJO class for Place*/
package com.mastercard.CodeChallenge.web.model;

import java.util.HashSet;
import java.util.Set;

public class Places {
	//Variables
	private String origin;
	private String destination;
	private Set<String> connectingCitySet = new HashSet<String>();

	//Constructor 
	public Places(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}
	
	//Getters & Setters
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Set<String> getConnectingCitySet() {
		return connectingCitySet;
	}

	public void setConnectingCitySet(Set<String> connectingCitySet) {
		this.connectingCitySet = connectingCitySet;
	}

	@Override
	public String toString() {
		return "Places [origin=" + origin + ", destination=" + destination + ", connectingCitySet=" + connectingCitySet
				+ "]";
	}
}
