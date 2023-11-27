package com.example.som.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String wrtie(@RequestParam BoardCategory board_category,
						Model model) {
		BoardWriteForm writeForm = new BoardWriteForm();
		writeForm.setBoard_category(board_category);
		
		model.addAttribute("write", writeForm);
		
		return "board/write";
	}
	
	@PostMapping("write")
	public String write(@RequestParam BoardCategory board_category,
						@ModelAttribute("write") BoardWriteForm boardWriteForm) {
		
		log.info("board: {}", boardWriteForm);
		// 파라미터로 받은 boardWriteForm 객체를 Board 타입으로 변환
		Board board = BoardWriteForm.toBoard(boardWriteForm);
		// board 객체 작성자 및 카테고리 설정
		board.setMember_id("test@test.com");
		// board 객체 DB저장
		boardService.saveBoard(board);
		
		return "redirect:/board/notice?board_category=NOTICE";
	}
	
	@GetMapping("read")
	public String read(@RequestParam Long seq_id,
						Model model) {
		Board findBoardById = boardService.findBoardById(seq_id);
		model.addAttribute("board", findBoardById);
		
		return "board/read";
	}
}
