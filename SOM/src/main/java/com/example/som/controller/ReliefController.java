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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.file.SavedFile;
import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;
import com.example.som.model.relief.ReliefUpdateForm;
import com.example.som.model.relief.ReliefWriteForm;
import com.example.som.service.ReliefService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("relief")
@RequiredArgsConstructor
@Slf4j
public class ReliefController {
	
	private final ReliefService reliefService;
	
	@Value("${file.upload.path}")
    private String uploadPath;
	
	// 각 해소법 페이지 이동
	@GetMapping("list")
	public String list(@RequestParam ReliefCategory relief_category ,
			Model model) {
		log.info("category: {}", relief_category);
		
		// DB에서 카테고리에 맞는 게시물들을 List형식으로 받아온다.
		List<Relief> reliefs = reliefService.findReliefs(relief_category);
		
		// 찾아온 List를 model에 담아서 view로 넘겨준다.
		model.addAttribute("relief_category", relief_category);
		model.addAttribute("reliefs", reliefs);
		log.info("reliefs:{}", reliefs);
		return "relief/list";
	}
	

	// 게시글 작성페이지 이동
	@GetMapping("write")
	public String wrtie(@RequestParam ReliefCategory relief_category,
						Model model) {
		// 새로운 relief작성 형식 객체 생성
		ReliefWriteForm writeForm = new ReliefWriteForm();
		// 새로 생성한 객체의 카테고리를 지정
		writeForm.setRelief_category(relief_category);
		// 작성페이지로 객체를 model에 담아서 넘겨준다
		model.addAttribute("write", writeForm);
		
		return "relief/write";
	}
	
	// 게시글 작성 저장
	@PostMapping("write")
	public String write(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam ReliefCategory relief_category,
						@Validated @ModelAttribute("write") ReliefWriteForm reliefWriteForm,
						@RequestParam(required = false) MultipartFile file,
						BindingResult result) {
		log.info("filesize: {}", file.getSize());
		
		// validation 에러가 있으면 작성페이지로 다시 이동.
		if(result.hasErrors()) {
			return "relief/write";
		}
		
		log.info("relief: {}", reliefWriteForm);
		// 파라미터로 받은 reliefWriteForm 객체를 Relief 타입으로 변환
		Relief relief = ReliefWriteForm.toRelief(reliefWriteForm);
		// relief 객체 DB저장
		reliefService.saveRelief(relief, file);
		log.info("relief: {}", relief);
		
			return "redirect:/relief/list?relief_category=" + relief.getRelief_category();

	}

	
	// 게시글 조회
	@GetMapping("read")
	public String read(@RequestParam Long seq_id,
						Model model) {
		// seq_id가 같은 relief를 찾아서 반환해준다.
		Relief relief = reliefService.findReliefById(seq_id);
		// 첨부파일을 찾는다.
		SavedFile savedFile = reliefService.findFileBySeqId(seq_id);
		// 찾은 객체를 model에 저장
		model.addAttribute("relief", relief);
		model.addAttribute("file", savedFile);
		
		return "relief/read";
	}
	
	// 게시글 수정 페이지 이동
	@GetMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam Long seq_id,
						Model model) {
		// 수정할 게시물의 seq_id를 받아와서 DB에서 찾는다.
		Relief relief = reliefService.findReliefById(seq_id);
		
		// model에 찾은 객체를 update라는 이름으로 담아서 view로 보내준다.
		model.addAttribute("update", relief);
		
		// 첨부파일을 찾아서 model에 담아준다.
		SavedFile savedFile = reliefService.findFileBySeqId(seq_id);
		model.addAttribute("file", savedFile);
		
		return "relief/update";
	}
	
	// 게시글 수정 저장
	@PostMapping("update")
	public String update(@AuthenticationPrincipal PrincipalDetails userInfo,
						@ModelAttribute("update") ReliefUpdateForm reliefUpdateForm,
						@RequestParam Long seq_id,
						ReliefCategory relief_category) {
		log.info("update: {}", reliefUpdateForm);
		
		// 수정할 relief를 찾는다.
		Relief relief = reliefService.findReliefById(seq_id);
		
		
		// 찾은 relief 객체에 수정받은 제목과 내용으로 수정해준다.
		relief.setTitle(reliefUpdateForm.getTitle());
		relief.setContent(reliefUpdateForm.getContent());
		// 새롭게 수정된 객체로 DB를 update해준다.
		reliefService.updateRelief(relief);
		log.info("relief:{}" , relief);
		log.info("category:{}" , relief.getRelief_category());
		
		return "redirect:/relief/list?relief_category=" + relief.getRelief_category();
			
	}
		
	
	// 게시물 삭제
	@GetMapping("delete")
	public String remove(@AuthenticationPrincipal PrincipalDetails userInfo,
						@RequestParam Long seq_id) {
		// 삭제할 relief를 찾는다.
		Relief relief = reliefService.findReliefById(seq_id);
		ReliefCategory relief_category = relief.getRelief_category();
		
		// 게시글을 삭제
		reliefService.removeRelief(seq_id);
		
		return "redirect:/relief/list?relief_category=" + relief.getRelief_category();
			
	}

	
	@GetMapping("download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException {
		// 첨부파일 아이디로 첨부파일 정보를 가져온다.
		SavedFile savedFile = reliefService.findFileByFileId(id);
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
