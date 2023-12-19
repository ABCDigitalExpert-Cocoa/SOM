package com.example.som.model.hobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HobbyCategory {
	HOBBY("취미게시판");
	
	private final String description;
}
