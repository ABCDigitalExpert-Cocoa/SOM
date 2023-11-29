package com.example.som.model.member;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
	    private AdminType admin;
	    
	    @Builder
	    public Member(String member_id, String password, String member_name, String nickname, LocalDate birth,
	    				String region, String mbti, String phone, String gender, AdminType admin) {
	    	this.member_id = member_id;
	    	this.password = password;
	    	this.member_name = member_name;
	    	this.nickname = nickname;
	    	this.birth = birth;
	    	this.region = region;
	    	this.mbti = mbti;
	    	this.phone = phone;
	    	this.gender = gender;
	    	this.admin = admin;
	    }
}
