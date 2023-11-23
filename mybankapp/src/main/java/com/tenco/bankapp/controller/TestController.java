package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // IoC에 대상()
@RequestMapping("/temp")
public class TestController {

	
	// Get
	// 주소설계 - http://localhost:80/temp-test
	// yml 파일에서 
	// prefix: /WEB-INF/view/ 
	// suffix: .jsp해놨음	
	@GetMapping("/test")
	public String tempTest() {			

		return "temp";
	}
	
	// GET
	// 주소 설계 http://localhost:80/temp/main-page
	@GetMapping("/main-page")
	public String tempMainPage() {
		return "main";
	}
}
