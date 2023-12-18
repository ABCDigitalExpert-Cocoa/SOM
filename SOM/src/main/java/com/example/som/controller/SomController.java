package com.example.som.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.diary.Diary;
import com.example.som.repository.SomMapper;
import com.example.som.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("mySpace/som")
@RequiredArgsConstructor
@Slf4j
public class SomController {
	
	private final SomMapper somMapper;
	private final MemberService memberService;
	
	@GetMapping("main")
	public String som(@AuthenticationPrincipal PrincipalDetails userInfo,
						Model model) {
		int level = 0;
		int point = memberService.findMember(userInfo.getUsername()).getPoint();
		if(point < 10) {
			level = 0;
		} else if(point >= 10 && point < 50) {
			level = 1;
		} else if(point >= 50 && point < 120) {
			level = 2;
		} else if(point >= 120 && point < 210) {
			level = 3;
		} else if(point >= 210 && point < 320) {
			level = 4;
		} else if(point >= 320) {
			level = 5;
		}
		
		List<Long> stress = somMapper.getRecentStress(userInfo.getUsername());
		List<String> testDate = somMapper.getRecentTestDate(userInfo.getUsername());
		List<Diary> diary = somMapper.getRecentDiary(userInfo.getUsername());
		
		log.info("point: {}", userInfo.getMember().getPoint());
		
		model.addAttribute("stress", stress);
		model.addAttribute("date", testDate);
		model.addAttribute("diary", diary);
		model.addAttribute("pointLevel", level);
		
		return "mySpace/som/main";
	}
	

}
