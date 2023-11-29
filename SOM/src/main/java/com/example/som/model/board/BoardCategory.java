package com.example.som.model.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardCategory {
	NOTICE("Notice");
	
	private final String description;
	
}