package com.example.som.model.board;

public enum BoardCategory {
	NOTICE("공지 게시판");
	
	private String description;
	
	private BoardCategory(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
