package com.example.music.service.exception;

public class BandNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String bandName;
	private final String description;
	
	private BandNotFoundException() {
		// hide constructor without parameters
		super();
		this.bandName = "";
		this.description = "";
	}
	
	public BandNotFoundException(String bandName, String description ) {
		super();
		this.bandName = bandName;
		this.description = description;
	}

	public String getBandName() {
		return bandName;
	}
	
	public String getDescription() {
		return description;
	}
}
