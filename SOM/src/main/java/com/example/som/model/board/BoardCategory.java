package com.example.som.model.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardCategory {
	NOTICE("Notice"),
	WORRY("Worry");
	
	private final String description;
	
}
