package com.example.som.model.diary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emotion {
	happy("행복"),
	funny("즐거움"),
	good("좋음"),
	bad("나쁨"),
	sad("슬픔");
	
	private final String description;

}
