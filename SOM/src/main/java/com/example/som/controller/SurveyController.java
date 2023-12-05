package com.example.som.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.som.model.survey.SurveyResultData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class SurveyController {
	
	//설문조사 페이지 이동
	@GetMapping("/survey")
	public String showSurveyAskList() { 
		return "/user/survey";
	}
	
	@PostMapping("/submit_survey")
	public String showSurveyResult(@ModelAttribute SurveyResultData surveyResultData, Model model) { 
		// surveyResultDat를 이용하여 데이터 처리
		
		// 각 응답의 점수를 더해서 totalScore 계산
        int totalScore = surveyResultData.getstressLevel_1Score() +
                         surveyResultData.getStressLevel_2Score() +
                         surveyResultData.getStressLevel_3Score() +
                         surveyResultData.getStressLevel_4Score() +
                         surveyResultData.getStressLevel_5Score() +
                         surveyResultData.getStressLevel_6Score() +
                         surveyResultData.getStressLevel_7Score() +
                         surveyResultData.getStressLevel_8Score() +
                         surveyResultData.getStressLevel_9Score() +
                         surveyResultData.getStressLevel_10Score();
        
        log.info("totalScore: {}", totalScore);
        
		// 필요한 데이터를 모델에 추가
		model.addAttribute("totalScore", totalScore);
		return "/user/submit_survey";
	}
	
//	@GetMapping("/submit_survey")
//	public String showSurveyResultPage() {
//	    // GET 요청을 처리하는 코드 추가
//	    return "/user/submit_survey";
//	}
	
	
		

}
