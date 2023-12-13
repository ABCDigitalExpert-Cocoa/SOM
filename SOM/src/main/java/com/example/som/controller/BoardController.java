package com.example.som.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.board.BoardUpdateForm;
import com.example.som.model.board.BoardWriteForm;
import com.example.som.model.file.SavedFile;
import com.example.som.service.BoardService;
import com.example.som.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	
	private final BoardService boardService;
	
	// 페이징 상수 값
	final int coutPerPage = 7;
	final int pagePerGroup = 5;
	
	@Value("${file.upload.path}")
    private String uploadPath;
	
	// 게시판 글 목록 출력
	@GetMapping("list")
	public String readNotice(@RequestParam(value = "page", defaultValue = "1") int page,
							@RequestParam BoardCategory board_category,
							Model model) {
		log.info("category: {}", board_category);
		
		int total = boardService.getTotal(board_category);
		
		PageNavigator navi = new PageNavigator(coutPerPage, pagePerGroup, page, total);
		
		// DB에서 카테고리에 맞는 게시물들을 List형식으로 받아온다.
		List<Board> boards = boardService.findBoards(board_category, navi.getStartRecord(), navi.getCountPerPage());
		// 찾아온 List를 model에 담아서 view로 넘겨준다.
		model.addAttribute("boards", boards);
		model.addAttribute("navi", navi);
		model.addAttribute("board_category", board_category);
		
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
		

			return "/board/write";
	}
	
	// 게시글 작성 저장
	@PostMapping("write")
	public String write(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam BoardCategory board_category,
						@Validated @ModelAttribute("write") BoardWriteForm boardWriteForm,
						@RequestParam(required = false) MultipartFile file,
						BindingResult result) {
		log.info("filesize: {}", file.getSize());
		
		// validation 에러가 있으면 작성페이지로 다시 이동.
		if(result.hasErrors()) {
			return "board/write";
		}
		
		log.info("board: {}", boardWriteForm);
		// 파라미터로 받은 boardWriteForm 객체를 Board 타입으로 변환
		Board board = BoardWriteForm.toBoard(boardWriteForm);
		// board 객체 작성자 및 카테고리 설정
		board.setMember_id(userInfo.getUsername());
		// board 객체 DB저장
		boardService.saveBoard(board, file);
		
		return "redirect:/board/list?board_category=" + board.getBoard_category();
		
	}
	
	// 게시글 조회
	@GetMapping("read")
	public String read(@RequestParam Long seq_id,
						Model model) {
		// seq_id가 같은 board를 찾아서 반환해준다.
		Board board = boardService.findBoardById(seq_id);
		// 첨부파일을 찾는다.
		SavedFile savedFile = boardService.findFileBySeqId(seq_id);
		// 찾은 객체를 model에 저장
		model.addAttribute("board", board);
		model.addAttribute("file", savedFile);
		
		return "board/read";
	}
	
	// 게시글 수정 페이지 이동
	@GetMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam Long seq_id,
						Model model) {
		// 수정할 게시물의 seq_id를 받아와서 DB에서 찾는다.
		Board board = boardService.findBoardById(seq_id);
		
		// 게시물이 없거나 작성자가 로그인한 사용자와 다를 경우 목록창으로 돌아간다.
		if(board == null || !board.getMember_id().equals(userInfo.getUsername())) {
			return "redirect:/board/list?board_category=" + board.getBoard_category();
		}
		
		// model에 찾은 객체를 update라는 이름으로 담아서 view로 보내준다.
		model.addAttribute("update", Board.toBoardUpdateForm(board));
		
		// 첨부파일을 찾아서 model에 담아준다.
		SavedFile savedFile = boardService.findFileBySeqId(seq_id);
		model.addAttribute("file", savedFile);
		
		return "board/update";
	}
	
	// 게시글 수정 저장
	@PostMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@Validated @ModelAttribute("update") BoardUpdateForm boardUpdateForm,
						@RequestParam Long seq_id,
						BindingResult result,
						@RequestParam(required = false) MultipartFile file) {
		log.info("update: {}", boardUpdateForm);
		
		if(result.hasErrors()) {
			return "board/update";
		}
		
		// 수정할 board를 찾는다.
		Board board = boardService.findBoardById(seq_id);
		
		// 게시물이 없거나 작성자가 로그인한 사용자와 다를 경우 목록창으로 돌아간다.
		if(board == null || !board.getMember_id().equals(userInfo.getUsername())) {
			return "redirect:/board/list?board_category=" + board.getBoard_category();
		}
		
		// 찾은 board 객체에 수정받은 제목과 내용으로 수정해준다.
		board.setTitle(boardUpdateForm.getTitle());
		board.setContent(boardUpdateForm.getContent());
		// 새롭게 수정된 객체로 DB를 update해준다.
		boardService.updateBoard(board, boardUpdateForm.isFileRemoved(), file);
	
			return "redirect:/board/list?board_category=" + board.getBoard_category();
		
	}
	
	// 게시물 삭제
	@GetMapping("delete")
	public String remove(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam Long seq_id) {
		// 삭제할 board를 찾는다.
		Board board = boardService.findBoardById(seq_id);
		
		// 게시물이 없거나 작성자가 로그인한 사용자와 다를 경우 조회창으로 돌아간다.
		if(board == null || !board.getMember_id().equals(userInfo.getUsername())) {
			return "redirect:/board/read?seq_id=" + board.getSeq_id();
		}
		
		// 게시글을 삭제
		boardService.removeBoard(seq_id);
		
			return "redirect:/board/list?board_category=" + board.getBoard_category();
	}
	
	
	@GetMapping("download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException {
		// 첨부파일 아이디로 첨부파일 정보를 가져온다.
		SavedFile savedFile = boardService.findFileByFileId(id);
		// 다운로드 하려는 파일의 절대경로 값을 만든다.
		String fullPath = uploadPath + "/" + savedFile.getSaved_filename();
        UrlResource resource = new UrlResource("file:" + fullPath);
        // 한글 파일명이 깨지지 않도록 UTF-8로 파일명을 인코딩한다.
        String encodingFileName = UriUtils.encode(savedFile.getOriginal_filename(), StandardCharsets.UTF_8);
        // 응답헤더에 담을 Content Disposition 값을 생성한다.
        String contentDisposition = "attachment; filename=\"" + encodingFileName + "\"";
       
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
