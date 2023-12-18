package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    // 멤버 포인트 업데이트
    void addPoint(Member member);
    // 멤버 랭킹 
    List<Member> getRanking();
    
    // 이미 읽은 글인지 확인
    //int hasReadRelief(@Param("member_id") String member_id, @Param("seq_id") Long seq_id);


    // 읽은 글 정보 저장
   // void saveReadRelief(@Param("member_id") String member_id, @Param("seq_id") Long seq_id);
}
