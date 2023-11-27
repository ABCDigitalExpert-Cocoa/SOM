package com.example.som.model.member;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Member {
	 private String member_id;
	    private String password;
	    private String member_name;
	    private String nickname;
	    private LocalDate birth;
	    private String region;
	    private String mbti;
	    private String phone;
	    private String gender;
}
