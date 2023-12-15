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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("mySpace/som")
@RequiredArgsConstructor
@Slf4j
public class SomController {
	
	private final SomMapper somMapper;
	
	@GetMapping("main")
	public String som(@AuthenticationPrincipal PrincipalDetails userInfo,
						Model model) {
		List<Long> stress = somMapper.getRecentStress(userInfo.getUsername());
		List<String> testDate = somMapper.getRecentTestDate(userInfo.getUsername());
		List<Diary> diary = somMapper.getRecentDiary(userInfo.getUsername());
		
		model.addAttribute("stress", stress);
		model.addAttribute("date", testDate);
		model.addAttribute("diary", diary);
		
		return "mySpace/som/main";
	}
	

}
