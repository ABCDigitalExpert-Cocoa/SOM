package com.example.som.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.board.BoardWriteForm;
import com.example.som.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("notice")
	public String readNotice(@RequestParam BoardCategory board_category,
							Model model) {
		log.info("category: {}", board_category);
		
		List<Board> boards = boardService.findBoards(board_category);
		model.addAttribute("boards", boards);
		
		return "board/notice";
	}
	
	@GetMapping("write")
	public String wrtie(Model model) {
		model.addAttribute("write", new BoardWriteForm());
		
		return "board/write";
	}
}
