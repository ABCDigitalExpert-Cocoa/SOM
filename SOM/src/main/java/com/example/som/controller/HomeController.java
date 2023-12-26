package com.example.som.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.som.config.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails userInfo) {
        log.info("userInfo:{}", userInfo);
    	
    	return "index";
    }

}
