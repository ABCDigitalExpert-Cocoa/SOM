package com.example.som.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.test.Test;
import com.example.som.repository.TestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
	
	private final TestMapper testMapper;
	
	//설문조사 페이지 이동
	@GetMapping("/stress")
	public String showSurveyAskList() { 
		return "/test/stress";
	}
	
	@ResponseBody
	@PostMapping("/stress")
	public ResponseEntity<String> saveResult(@ModelAttribute Test test,
											HttpServletResponse response,
											@AuthenticationPrincipal PrincipalDetails userInfo) { 
		log.info("result: {}", test);
		
		Cookie cookie = new Cookie("stress", test.getTest_result());
		cookie.setPath("/");
		response.addCookie(cookie);
		
		if(userInfo != null) {
			test.setMember_id(userInfo.getUsername());
			test.setStressLevel();
			testMapper.saveTest(test);
			
			Cookie oldCookie = new Cookie("stress", null);
			oldCookie.setPath("/");
			oldCookie.setMaxAge(0);
			response.addCookie(oldCookie);
		}
		
		return ResponseEntity.ok("result ok");
	}
	

}
