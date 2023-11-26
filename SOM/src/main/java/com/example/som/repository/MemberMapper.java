package com.example.som.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.member.Member;

@Mapper
public interface MemberMapper {
    void saveMember(Member member);
    Member findMember(String member_id);
}
