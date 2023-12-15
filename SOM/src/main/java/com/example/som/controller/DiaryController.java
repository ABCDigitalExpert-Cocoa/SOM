package com.example.som.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("diary")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
	
	@GetMapping("list")
	public String list() {
		
		
		return "diary/list";
	}

}
