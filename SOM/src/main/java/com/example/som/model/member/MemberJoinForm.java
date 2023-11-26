package com.example.som.model.member;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import lombok.Data;

@Data
public class MemberJoinForm {
    @Size(min = 4, max = 100)
    private String member_id;
    @Size(min = 4, max = 50)
    private String password;
    @NotBlank
    private String member_name;
    @NotNull
    private String nickname;
    private String age;
    private String region;
    private String mbti;
    private String phone;
    private String gender;
    
    

    public static Member toMember(MemberJoinForm memberJoinForm) {
        Member member = new Member();
        member.setMember_id(memberJoinForm.getMember_id());
        member.setPassword(memberJoinForm.getPassword());
        member.setMember_name(memberJoinForm.getMember_name());
        member.setNickname(memberJoinForm.getNickname());
        member.setAge(memberJoinForm.getAge());
        member.setRegion(memberJoinForm.getRegion());
        member.setMbti(memberJoinForm.getMbti());
        member.setPhone(memberJoinForm.getPhone());
        member.setGender(memberJoinForm.getGender());
        return member;
    }
}