package com.tenco.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfullException;
import com.tenco.bankapp.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired // DI 처리
	private UserService userService;

	// DI 처리 다른 방법
	// public UserController(UserService userservice) {
	//
	// }

	// 회원 가입 페이지 요청
	// http://localhost:80/user/sign-up
	@GetMapping("/sign-up")
	public String signUP() {
		return "user/signUp";
	}

	// 로그인 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}

	/**
	 * 
	 * @param dto
	 * @return 리다이렉트 로그인 페이지 처리
	 */
	// DTO - Object Mapper
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto dto) {

		// 1. 유효성 검사
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfullException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfullException("fullname을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		int resultRowCount = userService.signUp(dto);
		if (resultRowCount != 1) {
			// 다른처리
		}
		return "redirect:/user/sign-in";
	}
}
