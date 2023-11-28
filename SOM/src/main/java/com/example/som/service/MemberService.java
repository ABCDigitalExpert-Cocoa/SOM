package com.example.som.service;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public void saveMember(Member member) {
		String rawPassword = member.getPassword();
		//비크립트 방식으로 암호화
		String encPassword = passwordEncoder.encode(rawPassword);
		
		member.setPassword(encPassword);
		
		log.info("encPassword:{}", encPassword);
		memberMapper.saveMember(member);
	}
	
	public Member findMember(String member_id) {
		return memberMapper.findMember(member_id);
	}
		
}
