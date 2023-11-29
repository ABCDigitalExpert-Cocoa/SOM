package com.example.som.model.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AdminType {
	ROLE_USER("사용자"),
	ROLE_ADMIN("관리자");
	
	private final String description;

}
