package com.example.som.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.som.model.diary.Diary;
import com.example.som.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("diary")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
	
	private final DiaryService diaryService;
	
	@GetMapping("list")
	public String list(Model model) {
		List<Diary> findAllOpenDiary = diaryService.findAllOpenDiary();
//		log.info("findAllOpenDiary: {}", findAllOpenDiary);
		
		model.addAttribute("diarys", findAllOpenDiary);
		
		return "diary/list";
	}

}
