package com.example.som.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.member.Member;
import com.example.som.model.member.MemberUpdateForm;

@Mapper
public interface MemberMapper {
	//회원정보 저장
    void saveMember(Member member);
    //회원 검색
    Member findMember(String member_id);
    // 회원정보 수정
    void updateMember(MemberUpdateForm updateMember);
}
