package com.example.som.model.hobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HobbyCategory {
	baking("베이킹"),
	ring("반지"),
	dating("데이트"),
	perfume("향수"),
	sports("스포츠"),
	candle("캔들"),
	music("음악"),
	leather("가죽");
	
	private final String description;
}
