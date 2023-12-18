package com.example.som.model.member;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberUpdateForm {
	@Size(min = 4, max = 100)
	@NotBlank
	@Email
	private String member_id;
	@NotBlank
	private String password;
    @Size(min = 4, max = 100)
    @NotBlank
    private String member_name;
    @NotBlank
    private String nickname;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String region;
    private String mbti;
    @Size(min=10, max=11)
    private String phone;
    private String gender;
    private int point;
}
