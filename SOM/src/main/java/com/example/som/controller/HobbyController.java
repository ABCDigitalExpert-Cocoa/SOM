package com.example.som.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
import com.example.som.model.file.SavedFile;
import com.example.som.model.hobby.HobbyBoard;
import com.example.som.model.hobby.HobbyBoardUpdateForm;
import com.example.som.model.hobby.HobbyBoardWriteForm;
import com.example.som.model.hobby.Region;
import com.example.som.service.HobbyService;
import com.example.som.service.SavedFileService;
import com.example.som.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("hobby")
@RequiredArgsConstructor
@Slf4j
public class HobbyController {
	
	private final HobbyService hobbyService;
	private final SavedFileService savedFileService;
	
	// 페이징 상수 값
	final int coutPerPage = 9;
	final int pagePerGroup = 5;
	
	@Value("${file.upload.path}")
    private String uploadPath;
	
	// 게시판 글 목록 출력
	@GetMapping("list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page,
						@RequestParam(required = false) Region region,
						Model model) {
		int total = hobbyService.getTotal(region);
		
		PageNavigator navi = new PageNavigator(coutPerPage, pagePerGroup, page, total);
		
		// DB에서 카테고리에 맞는 게시물들을 List형식으로 받아온다.
		List<HobbyBoard> hobbyBoards = hobbyService.findBoards(region, navi.getStartRecord(), navi.getCountPerPage());
		log.info("boards: {}", hobbyBoards);
		
		// 찾아온 List를 model에 담아서 view로 넘겨준다.
		model.addAttribute("hobbyBoards", hobbyBoards);
		model.addAttribute("navi", navi);
		
		return "hobby/list";
	}
	
	// 게시글 작성페이지 이동
	@GetMapping("write")
	public String wrtie(Model model) {
		// 새로운 board작성 형식 객체 생성
		HobbyBoardWriteForm writeForm = new HobbyBoardWriteForm();
		// 작성페이지로 객체를 model에 담아서 넘겨준다
		model.addAttribute("write", writeForm);
		
		return "hobby/write";
	}
	
	// 게시글 작성 저장
	@PostMapping("write")
	public String write(@AuthenticationPrincipal PrincipalDetails userInfo,
						@Validated @ModelAttribute("write") HobbyBoardWriteForm hobbyBoardWriteForm,
						BindingResult result,
						@RequestParam(required = false) MultipartFile file) {
		log.info("filesize: {}", file.getSize());
		
		// validation 에러가 있으면 작성페이지로 다시 이동.
		if(result.hasErrors()) {
			return "hobby/write";
		}

		log.info("board: {}", hobbyBoardWriteForm);
		
		// 가격 입력값이 없을 경우 0으로 세팅
		if(hobbyBoardWriteForm.getPrice().isEmpty()) {
			hobbyBoardWriteForm.setPrice("0");
		}
		
		HobbyBoard hobbyBoard = HobbyBoardWriteForm.toHobbyBoard(hobbyBoardWriteForm);

		// DB저장
		hobbyService.saveBoard(hobbyBoard, file);
		
		return "redirect:/hobby/list";
	}
	
	// 게시글 조회
	@GetMapping("read")
	public String read(@RequestParam Long hobby_id,
						Model model) {
		
		HobbyBoard hobbyBoard = hobbyService.readHobbyBoard(hobby_id);

		// 첨부파일을 찾는다.
		SavedFile savedFile = savedFileService.findHobbyFile(hobby_id);
		
		// 찾은 객체를 model에 저장
		model.addAttribute("hobby", hobbyBoard);
		model.addAttribute("file", savedFile);
		
		return "hobby/read";
	}
	
	// 게시글 수정 페이지 이동
	@GetMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam Long hobby_id,
						Model model) {
		// 수정할 게시물 찾기
		HobbyBoard hobbyBoard = hobbyService.findBoardById(hobby_id);
		
		// 게시물이 없을 경우 목록창으로 돌아간다.
		if(hobbyBoard == null) {
			return "redirect:/hobby/list";
		}
		
		// model에 찾은 객체를 update라는 이름으로 담아서 view로 보내준다.
		model.addAttribute("update", HobbyBoard.toBoardUpdateForm(hobbyBoard));
		
		// 첨부파일을 찾아서 model에 담아준다.
		SavedFile savedFile = savedFileService.findHobbyFile(hobby_id);
		model.addAttribute("file", savedFile);
		
		return "hobby/update";
	}
	
	// 게시글 수정 저장
	@PostMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@Validated @ModelAttribute("update") HobbyBoardUpdateForm hobbyBoardUpdateForm,
						BindingResult result,
						@RequestParam Long hobby_id,
						@RequestParam(required = false) MultipartFile file) {
		log.info("update: {}", hobbyBoardUpdateForm);
		
		if(result.hasErrors()) {
			return "hobby/update";
		}
		
		// 수정할 board를 찾는다.
		HobbyBoard hobbyBoard = hobbyService.findBoardById(hobby_id);
		
		// 게시물이 없을 경우 목록창으로 돌아간다.
		if(hobbyBoard == null) {
			return "redirect:/hobby/list";
		}
		
		// 찾은 board 객체에 수정받은 제목과 내용으로 수정해준다.
		hobbyBoard.setTitle(hobbyBoardUpdateForm.getTitle());
		hobbyBoard.setContent(hobbyBoardUpdateForm.getContent());
		hobbyBoard.setRegion(hobbyBoardUpdateForm.getRegion());
		hobbyBoard.setHobby_category(hobbyBoardUpdateForm.getHobby_category());
		hobbyBoard.setPrice(hobbyBoardUpdateForm.getPrice());
		// 새롭게 수정된 객체로 DB를 update해준다.
		hobbyService.updateBoard(hobbyBoard, hobbyBoardUpdateForm.isFileRemoved(), file);
		
		return "redirect:/hobby/list";
	}
	
	// 게시물 삭제
	@GetMapping("delete")
	public String remove(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam Long hobby_id) {
		// 삭제할 board를 찾는다.
		HobbyBoard hobbyBoard = hobbyService.findBoardById(hobby_id);
		
		// 게시물이 없을 경우 조회창으로 돌아간다.
		if(hobbyBoard == null) {
			return "redirect:/hobby/list";
		}
		
		// 게시글을 삭제
		hobbyService.removeBoard(hobby_id);
		
		return "redirect:/hobby/list";
	}
	
}
