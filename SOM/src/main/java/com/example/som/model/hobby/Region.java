package com.example.som.model.hobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
	seoul("서울"),
	busan("부산"),
	incheon("인천"),
	daegu("대구"),
	ulsan("울산"),
	gwangju("광주"),
	daejeon("대전"),
	gyeonggi("경기도"),
	gyeongnam("경상남도"),
	gyeongbuk("경상북도"),
	jeonnam("전라남도"),
	jeonbuk("전라북도"),
	choongnam("충청남도"),
	choongbuk("충청북도"),
	gangwon("강원도"),
	jeju("제주도");
	
	private final String description;
}
