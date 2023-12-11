package com.example.som.model.relief;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReliefCategory {
	MBTI("mbti"),
	MUSIC("music"),
	WORKOUT("workout");
	
	private final String description;
	
}
