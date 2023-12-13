package com.example.som.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.diary.Diary;
import com.example.som.model.diary.DiaryWriteForm;
import com.example.som.model.file.SavedFile;
import com.example.som.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("mySpace/diary")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
	
	private final DiaryService diaryService;
	
	@GetMapping("list")
	public String diaryList(@AuthenticationPrincipal PrincipalDetails userInfo,
							Model model) {
		List<Diary> diarys = diaryService.findAllDiary(userInfo.getUsername());
		model.addAttribute("diarys", diarys);
		
		return "mySpace/diary/list";
	}
	
	@GetMapping("write")
	public String writeDiary(Model model) {
		DiaryWriteForm writeForm = new DiaryWriteForm();
		model.addAttribute("write", writeForm);
		
		return "mySpace/diary/write";
	}
	
	@PostMapping("write")
	public String writeDiary(@Validated @ModelAttribute("write") DiaryWriteForm writeForm,
							@RequestParam(required = false) MultipartFile file,
							@AuthenticationPrincipal PrincipalDetails userInfo,
							BindingResult result) {
		if(result.hasErrors()) {
			return "mySpace/diary/write";
		}
		
		Diary diary = DiaryWriteForm.toDiary(writeForm);
		diary.setMember_id(userInfo.getUsername());
		log.info("write: {}", diary);
		
		diaryService.saveDiary(diary, file);

		return "redirect:/mySpace/diary/list";
	}
}
