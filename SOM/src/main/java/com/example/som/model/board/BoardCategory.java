package com.example.som.model.board;

public enum BoardCategory {
	NOTICE("Notice");
	
	private String description;
	
	private BoardCategory(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
