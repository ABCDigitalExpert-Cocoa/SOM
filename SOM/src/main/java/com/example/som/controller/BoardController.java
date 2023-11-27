package com.example.som.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.som.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardSevice;
	
	@GetMapping("notice")
	public String readNotice() {
		
		return "board/notice";
	}
	
	@GetMapping("write")
	public String wrtie() {
		
		return "board/write";
	}
}
