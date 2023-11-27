package com.example.som.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.som.model.member.Member;
import com.example.som.model.member.MemberJoinForm;
import com.example.som.repository.MemberMapper;
import com.example.som.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	// 회원가입 페이지 이동
    @GetMapping("join")
    public String joinForm(Model model) {
        // join.html 의 필드 세팅을 위해 model 에 빈 MemberJoinForm 객체 생성하여 저장한다
        model.addAttribute("joinForm", new MemberJoinForm());
        // user/join.html 페이지를 리턴한다.
        return "user/join";
    }
    
 // 회원가입
    @PostMapping("join")
    public String join(@Validated @ModelAttribute("joinForm") MemberJoinForm joinForm,
                       BindingResult result) {
        log.info("joinForm: {}", joinForm);

        // validation 에 에러가 있으면 가입시키지 않고 user/join.html 페이지로 돌아간다.
        if (result.hasErrors()) {
            return "user/join";
        }
        // 이메일 주소에 '@' 문자가 포함되어 있는지 확인한다.
        if (!joinForm.getMember_id().contains("@")) {
            // BindingResult 객체에 GlobalError 를 추가한다.
            result.reject("emailError", "아이디 형식은 이메일형식이어야 합니다.");
            // user/join.html 페이지를 리턴한다.
            return "user/join";
        }
        
        // 사용자로부터 입력받은 아이디로 데이터베이스에서 Member 를 검색한다.
        Member member = memberService.findMember(joinForm.getMember_id());
        // 사용자 정보가 존재하면
        if (member != null) {
            log.info("이미 가입된 이메일 입니다.");
            // BindingResult 객체에 GlobalError 를 추가한다.
            result.reject("duplicate ID", "이미 가입된 이메일 입니다.");
            // user/join.html 페이지를 리턴한다.
            return "user/join";
        }
        // MemberJoinForm 객체를 Member 타입으로 변환하여 데이터베이스에 저장한다.
        memberService.saveMember(MemberJoinForm.toMember(joinForm));
        // 메인 페이지로 리다이렉트한다.
        return "redirect:/";
    }
	
	@GetMapping("login")
	public String loginForm() {
		return "user/login";
	}
}
