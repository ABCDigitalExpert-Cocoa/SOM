package com.example.som.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.diary.DiaryWriteForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("mySpace")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
	
	@GetMapping("diary")
	public String diaryList(@AuthenticationPrincipal PrincipalDetails userInfo) {
		
		
		return "mySpace/diary";
	}
	
	@GetMapping("writeDiary")
	public String writeDiary(Model model,
							@AuthenticationPrincipal PrincipalDetails userInfo) {
		DiaryWriteForm writeForm = new DiaryWriteForm();
		writeForm.setMember_id(userInfo.getUsername());
		model.addAttribute("write", writeForm);
		
		
		return "mySpace/writeDiary";
	}
}
