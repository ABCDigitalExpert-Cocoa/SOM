package com.example.som.model.member;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberJoinForm {
    @Size(min = 4, max = 100)
    @NotBlank
    @Email
    private String member_id;
    @Size(min = 4, max = 100)
    @NotBlank
    private String password;
    @NotBlank
    private String member_name;
    @NotBlank
    private String nickname;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String region;
    @NotBlank(message = "MBTI를 선택해 주세요.")
    private String mbti;
    @Size(min=10, max=11)
    private String phone;
    private String gender;
    
    

    public static Member toMember(MemberJoinForm memberJoinForm) {
        Member member = new Member();
        member.setMember_id(memberJoinForm.getMember_id());
        member.setPassword(memberJoinForm.getPassword());
        member.setMember_name(memberJoinForm.getMember_name());
        member.setNickname(memberJoinForm.getNickname());
        member.setBirth(memberJoinForm.getBirth());
        member.setRegion(memberJoinForm.getRegion());
        member.setMbti(memberJoinForm.getMbti());
        member.setPhone(memberJoinForm.getPhone());
        member.setGender(memberJoinForm.getGender());
        return member;
    }
}
