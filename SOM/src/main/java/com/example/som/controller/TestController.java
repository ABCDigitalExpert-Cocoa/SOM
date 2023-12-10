package com.example.som.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.som.model.test.StressResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
	
	//설문조사 페이지 이동
	@GetMapping("/stress")
	public String showSurveyAskList() { 
		return "/test/stress";
	}
	
	@ResponseBody
	@PostMapping("/stress")
	public ResponseEntity<String> saveResult(@ModelAttribute StressResult result,
											HttpServletResponse response) { 
		log.info("result: {}", result);
		
		Cookie cookie = new Cookie("stress", result.getResult());
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return ResponseEntity.ok("result ok");
	}
	

}
