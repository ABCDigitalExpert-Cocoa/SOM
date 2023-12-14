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
import com.example.som.model.diary.DiaryUpdateForm;
import com.example.som.model.diary.DiaryWriteForm;
import com.example.som.model.file.SavedFile;
import com.example.som.service.DiaryService;
import com.example.som.service.SavedFileService;
import com.example.som.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("mySpace/diary")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
	
	private final DiaryService diaryService;
	private final SavedFileService savedFileService;
	
	// 페이징 상수 값
	final int coutPerPage = 9;
	final int pagePerGroup = 5;
	
	@GetMapping("list")
	public String diaryList(@RequestParam(value = "page", defaultValue = "1") int page,
							@AuthenticationPrincipal PrincipalDetails userInfo,
							Model model) {
		int total = diaryService.getTotal(userInfo.getUsername());
		
		PageNavigator navi = new PageNavigator(coutPerPage, pagePerGroup, page, total);
		
		List<Diary> diarys = diaryService.findAllDiary(userInfo.getUsername(), navi.getStartRecord(), navi.getCountPerPage());
		model.addAttribute("diarys", diarys);
		model.addAttribute("navi", navi);
		
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
	
	@GetMapping("read")
	public String readDiary(@RequestParam Long diary_id,
							Model model) {
		Diary findDiary = diaryService.findDiary(diary_id);
		model.addAttribute("diary", findDiary);
		
		return "mySpace/diary/read";
	}
	
	@GetMapping("update")
	public String updateDiary(@RequestParam Long diary_id,
							@AuthenticationPrincipal PrincipalDetails userInfo,
							Model model) {
		Diary diary = diaryService.findDiary(diary_id);
		
		if(diary == null || !diary.getMember_id().equals(userInfo.getUsername())) {
			return "redirect:/mySpace/diary/list?member_id=" + userInfo.getUsername();
		}
		
		model.addAttribute("update", Diary.toDiaryUpdateForm(diary));
		
		SavedFile savedFile = savedFileService.findFileByDiaryId(diary_id);
		model.addAttribute("file", savedFile);
		
		return "mySpace/diary/update";
	}
	
	@PostMapping("update")
	public String updateDiary(@AuthenticationPrincipal PrincipalDetails userInfo,
								@Validated @ModelAttribute("update") DiaryUpdateForm diaryUpdateForm,
								@RequestParam Long diary_id,
								@RequestParam(required = false) MultipartFile file,
								BindingResult result) {
		log.info("update: {}", diaryUpdateForm);
		if(result.hasErrors()) {
			return "mySpace/diary/update";
		}
		
		Diary findDiary = diaryService.findDiary(diary_id);
		if(findDiary == null || !findDiary.getMember_id().equals(userInfo.getUsername())) {
			return "redirect:/mySpace/diary/list?member_id=" + userInfo.getUsername();
		}
		
		findDiary.setTitle(diaryUpdateForm.getTitle());
		findDiary.setContent(diaryUpdateForm.getContent());
		findDiary.setEmotion(diaryUpdateForm.getEmotion());
		findDiary.setOpen_or_not(diaryUpdateForm.getOpen_or_not());
		
		diaryService.updateDiary(findDiary, diaryUpdateForm.isFileRemoved(), file);
		
		return "redirect:/mySpace/diary/list?member_id=" + userInfo.getUsername();
	}
	
	@GetMapping("delete")
	public String removeDiary(@RequestParam Long diary_id,
								@AuthenticationPrincipal PrincipalDetails userInfo) {
		Diary findDiary = diaryService.findDiary(diary_id);
		
		if(findDiary == null || !findDiary.getMember_id().equals(userInfo.getUsername())) {
			return "redirect:/mySpace/diary/read?diary_id=" + diary_id;
		}
		
		diaryService.removeDiary(diary_id);
		
		return "redirect:/mySpace/diary/list?member_id=" + userInfo.getUsername();
	}
}
