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
import com.example.som.model.board.BoardUpdateForm;
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
	
	// 게시판 글 목록 출력
	@GetMapping("list")
	public String readNotice(@RequestParam BoardCategory board_category,
							Model model) {
		log.info("category: {}", board_category);
		
		// DB에서 카테고리에 맞는 게시물들을 List형식으로 받아온다.
		List<Board> boards = boardService.findBoards(board_category);
		// 찾아온 List를 model에 담아서 view로 넘겨준다.
		model.addAttribute("boards", boards);
		
		return "board/list";
	}
	
	// 게시글 작성페이지 이동
	@GetMapping("write")
	public String wrtie(@RequestParam BoardCategory board_category,
						Model model) {
		// 새로운 board작성 형식 객체 생성
		BoardWriteForm writeForm = new BoardWriteForm();
		// 새로 생성한 객체의 카테고리를 지정
		writeForm.setBoard_category(board_category);
		// 작성페이지로 객체를 model에 담아서 넘겨준다
		model.addAttribute("write", writeForm);
		
		return "board/write";
	}
	
	// 게시글 작성 저장
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
		
		return "redirect:/board/list?board_category=" + board_category;
	}
	
	// 게시글 조회
	@GetMapping("read")
	public String read(@RequestParam Long seq_id,
						Model model) {
		// seq_id가 같은 board를 찾아서 반환해준다.
		Board findBoardById = boardService.findBoardById(seq_id);
		// 찾은 객체를 model에 담아서 view에 전달
		model.addAttribute("board", findBoardById);
		
		return "board/read";
	}
	
	// 게시글 수정 페이지 이동
	@GetMapping("update")
	public String update(@RequestParam Long seq_id,
						Model model) {
		// 수정할 게시물의 seq_id를 받아와서 DB에서 찾는다.
		Board board = boardService.findBoardById(seq_id);
		// model에 찾은 객체를 update라는 이름으로 담아서 view로 보내준다.
		model.addAttribute("update", board);
		
		return "board/update";
	}
	
	// 게시글 수정 저장
	@PostMapping("update")
	public String update(@ModelAttribute("update") BoardUpdateForm boardUpdateForm,
						@RequestParam Long seq_id) {
		log.info("update: {}", boardUpdateForm);
		
		// 수정할 board를 찾는다.
		Board board = boardService.findBoardById(seq_id);
		// 찾은 board 객체에 수정받은 제목과 내용으로 수정해준다.
		board.setTitle(boardUpdateForm.getTitle());
		board.setContent(boardUpdateForm.getContent());
		// 새롭게 수정된 객체로 DB를 update해준다.
		boardService.updateBoard(board);
		
		return "redirect:/board/list?board_category=" + board.getBoard_category();
	}
}
