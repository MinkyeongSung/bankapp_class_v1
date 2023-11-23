package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // IoC에 대상()
public class TestController {

	
	// Get
	// 주소설계 - http://localhost:80/temp-test
	// yml 파일에서 
	// prefix: /WEB-INF/view/ 
	// suffix: .jsp해놨음	
	@GetMapping("/temp-test")
	public String tempTest() {
		

		return "temp";
	}
}
