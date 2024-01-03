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
import com.example.som.model.etc.EtcBoard;
import com.example.som.model.etc.EtcBoardUpdateForm;
import com.example.som.model.etc.EtcBoardWriteForm;
import com.example.som.model.file.SavedFile;
import com.example.som.service.EtcService;
import com.example.som.service.SavedFileService;
import com.example.som.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("test/etc")
@RequiredArgsConstructor
@Slf4j
public class EtcController {

	private final EtcService etcService;
	private final SavedFileService savedFileService;

	// 페이징 상수 값
	final int coutPerPage = 7;
	final int pagePerGroup = 5;

	@Value("${file.upload.path}")
	private String uploadPath;

	// 게시판 글 목록 출력
	@GetMapping("list")
	public String readEtc(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {

		int total = etcService.getTotal();

		PageNavigator navi = new PageNavigator(coutPerPage, pagePerGroup, page, total);

		List<EtcBoard> etcBoards = etcService.findBoards();
		model.addAttribute("navi", navi);
		model.addAttribute("etcBoards", etcBoards);
		return "test/etc/list";
	}

	@GetMapping("write")
	public String wrtieForm(Model model) {
		// 새로운 board작성 형식 객체 생성
		EtcBoardWriteForm writeForm = new EtcBoardWriteForm();
		// 작성페이지로 객체를 model에 담아서 넘겨준다
		model.addAttribute("write", writeForm);
		return "test/etc/write";
	}

	@PostMapping("write")
	public String write(@Validated @ModelAttribute("write") EtcBoardWriteForm etcBoardWriteForm,
			 			BindingResult result,
						@RequestParam(required = false) MultipartFile file) {

		log.info("filesize: {}", file.getSize());

		// validation 에러가 있으면 작성페이지로 다시 이동.
		if (result.hasErrors()) {
			return "test/etc/write";
		}
		log.info("board: {}", etcBoardWriteForm);
		// 파라미터로 받은 etcboardWriteForm 객체를 EtcBoard 타입으로 변환
		EtcBoard etcBoard = EtcBoardWriteForm.toEtcBoard(etcBoardWriteForm);
		// DB저장
		etcService.saveBoard(etcBoard, file);

		return "redirect:/test/etc/list";
	}

	// 게시글 조회
	@GetMapping("read")
	public String read(@RequestParam Long etc_id, Model model) {

		// etc_id가 같은 etcboard를 찾아서 반환해준다.
		EtcBoard etcBoard = etcService.readEtcBoard(etc_id);
		// 첨부파일을 찾는다.
		SavedFile savedFile = savedFileService.findEtcFile(etc_id);
		// 찾은 객체를 model에 저장
		model.addAttribute("etc", etcBoard);
		model.addAttribute("file", savedFile);

		return "test/etc/read";
	}

	// 게시글 수정 페이지 이동
	@GetMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo, @RequestParam Long etc_id, Model model) {
		// 수정할 게시물의 etc_id를 받아와서 DB에서 찾는다.
		EtcBoard etcBoard = etcService.findBoardById(etc_id);

		// 게시물이 없을 경우 목록창으로 돌아간다.
		if (etcBoard == null) {
			return "redirect:/test/etc/list";
		}

		// model에 찾은 객체를 update라는 이름으로 담아서 view로 보내준다.
		model.addAttribute("update", EtcBoard.toEtcBoardUpdateForm(etcBoard));

		// 첨부파일을 찾아서 model에 담아준다.
		SavedFile savedFile = savedFileService.findEtcFile(etc_id);
		model.addAttribute("file", savedFile);

		return "test/etc/update";
	}

	// 게시글 수정 저장
	@PostMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@Validated @ModelAttribute("update") EtcBoardUpdateForm etcBoardUpdateForm,
						BindingResult result,
						@RequestParam Long etc_id,
						@RequestParam(required = false) MultipartFile file) {
		
		log.info("update: {}", etcBoardUpdateForm);

		if (result.hasErrors()) {
			return "test/etc/update";
		}

		// 수정할 etcboard를 찾는다.
		EtcBoard etcBoard = etcService.findBoardById(etc_id);

		// 게시물이 없거나 작성자가 로그인한 사용자와 다를 경우 목록창으로 돌아간다.
		if (etcBoard == null) {
			return "redirect:/test/etc/list";
		}

		// 찾은 etcboard 객체에 수정받은 제목과 내용으로 수정해준다.
		etcBoard.setTitle(etcBoardUpdateForm.getTitle());
		etcBoard.setContent(etcBoardUpdateForm.getContent());

		// 새롭게 수정된 객체로 DB를 update해준다.
		etcService.updateBoard(etcBoard, etcBoardUpdateForm.isFileRemoved(), file);

		return "redirect:/test/etc/list";
	}

	// 게시물 삭제
	@GetMapping("delete")
	public String remove(@RequestParam Long etc_id) {
		// 삭제할 board를 찾는다.
		EtcBoard etcBoard = etcService.findBoardById(etc_id);

		if (etcBoard == null) {
			return "redirect:/test/etc/list";
		}

		// 게시글을 삭제
		etcService.removeBoard(etc_id);

		return "redirect:/test/etc/list";
	}
}