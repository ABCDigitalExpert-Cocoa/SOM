package com.example.som.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.som.model.member.Member;
import com.example.som.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly=true)
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberMapper memberMapper ;
	
	public void saveMember(Member member) {
		memberMapper.saveMember(member);
	}
	
	public Member findMember(String member_id) {
		return memberMapper.findMember(member_id);
	}
		
}
