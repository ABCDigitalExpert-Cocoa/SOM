package com.example.som.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.core.Authentication;
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

import com.example.som.config.PrincipalDetails;
import com.example.som.model.member.Member;
import com.example.som.model.member.MemberJoinForm;
import com.example.som.model.member.MemberLoginForm;
import com.example.som.model.member.MemberUpdateForm;
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
	public String loginForm(@RequestParam(value="error", required = false) boolean error,
							@RequestParam(value="message", required = false) String message,
							Model model) {
		log.info("error: {}", error);
		log.info("message: {}", message);
	
		if(error) {
			model.addAttribute("error", error);
			model.addAttribute("message", message);
		}
	
		model.addAttribute("login", new MemberLoginForm());
		return "user/login";
	}
	
	
	@GetMapping("login-success")
	public String loginSuccess() {
		log.info("로그인 성공");
	
		return "redirect:/";
	}
	
	@GetMapping("login-failed")
	public String loginFailed() {
		log.info("로그인 실패");
	
		return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		log.info("로그아웃 호출");
		// Request 객체에서 Session 정보를 가져온다.
		HttpSession session = request.getSession(false);
		// 세션이 존재하면 세션의 모든 데이터를 리셋한다.
		if (session != null) {
			session.invalidate();
		}
		// 메인 페이지로 리다이렉트 한다.
		return "redirect:/";
	}
	
	@GetMapping("update")
	public String updateForm(Model model, 
													@AuthenticationPrincipal PrincipalDetails principalDetails) {
	    
	    // 아이디를 기반으로 DB에서 회원 정보를 조회.
	    Member dbMember = memberService.findMember(principalDetails.getUsername());

	    if (dbMember == null) {
	        // DB에서 회원 정보를 찾지 못하면 리다이렉트.
	        return "redirect:/"; 
	    }

	    // DB에서 가져온 정보를 모델에 추가하여 폼에 전달.
	    model.addAttribute("update", dbMember);

	    // user/update.html 페이지를 리턴.
	    return "user/update";
	}



	
	// 회원정보 수정 저장
	@PostMapping("update")
	public String update(@RequestParam("member_id") String memberId,
	                     @Validated @ModelAttribute("update") MemberUpdateForm memberUpdateForm,
	                     BindingResult result) {

	    // validation 에 에러가 있으면 수정시키지 않고 user/update.html 페이지로 돌아간다.
	    if (result.hasErrors()) {
	        return "user/update";
	    }

	    // 사용자로부터 입력받은 아이디로 데이터베이스에서 Member 를 검색한다.
	    Member member = memberService.findMember(memberId);

	    // 수정하려는 회원의 ID와 현재 로그인한 멤버의 ID가 다르면 권한이 없는 것으로 처리
	    // (URL을 통한 다른 회원의 수정 시도 방지)
	    if (!member.getMember_id().equals(memberUpdateForm.getMember_id())) {
	        // 권한이 없는 경우에 대한 처리, 예를 들어 메인페이지로 리다이렉트
	        return "redirect:/";
	    }
	    
	    log.info("updateForm: {}", memberUpdateForm);
	    log.info("member: {}", member);

	    // MemberUpdateForm 엔티티를 업데이트하고 저장한다.
	    memberService.updateMember(memberUpdateForm);

	    // 메인 페이지로 리다이렉트한다.
	    return "redirect:/";
	}


	
}
