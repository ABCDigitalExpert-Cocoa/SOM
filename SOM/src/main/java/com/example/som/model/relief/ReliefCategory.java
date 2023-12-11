package com.example.som.model.relief;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReliefCategory {
	MBTI("MBTI"),
	STRESS_LEVEL("Stress");
	
	private final String description;
	
}
