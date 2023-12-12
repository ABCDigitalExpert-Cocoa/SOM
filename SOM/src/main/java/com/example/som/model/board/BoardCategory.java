package com.example.som.model.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardCategory {
	NOTICE("Notice"),
	COUNSEL("Counsel");
	
	private final String description;
	
}
