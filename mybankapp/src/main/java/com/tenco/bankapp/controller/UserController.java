package com.tenco.bankapp.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bankapp.dto.SignInFormDto;
import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.dto.response.KakaoProfile;
import com.tenco.bankapp.dto.response.OAuthToken;
import com.tenco.bankapp.handler.exception.CustomRestfullException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.service.UserService;
import com.tenco.bankapp.utils.Define;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired // DI 처리
	private UserService userService;

	@Autowired
	private HttpSession session;

	@Value("${tenco.key}")
	private String tencoKey;

	// DI 처리
//	public UserController(UserService userService) {
//		this.userService = userService;
//	}

	// 회원 가입 페이지 요청
	// http://localhost:80/user/sign-up
	@GetMapping("/sign-up")
	public String signUp() {
		return "user/signUp";
	}

	// 로그인 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}

	/**
	 * 회원 가입 처리
	 * 
	 * @param dto
	 * @return 리다이렉트 로그인 페이지
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto dto) {

		// 1. 유효성 검사
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfullException("fullname을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		// 사용자 프로필 이미지 등록 처리
		MultipartFile file = dto.getFile();
		if (file.isEmpty() == false) {

			// 파일 사이즈 체크
			if (file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfullException("파일 크기는 20MB 이상 클 수 없어요", HttpStatus.BAD_REQUEST);
			}

			try {
				// 업로드 파일 경로
				String saveDirectory = Define.UPLOAD_DIRECTORY;
				// 폴더가 없다면 오류 발생
				File dir = new File(saveDirectory);
				if (dir.exists() == false) {
					dir.mkdir(); // 폴더가 없다면 생성
				}
				// 파일 이름 (중복 처리 예방)
				UUID uuid = UUID.randomUUID();
				String fileName = uuid + "_" + file.getOriginalFilename();
				// 전체 경로 지정 생성
				String uploadPath = Define.UPLOAD_DIRECTORY + File.separator + fileName;
				System.out.println("uploadPath " + uploadPath);
				File destination = new File(uploadPath);

				// 반드시 사용
				file.transferTo(destination); // 실제 생성

				// 객체 상태 변경 (insert 처리 하기 위함 -> 쿼리 수정해야 함)
				dto.setOriginFileName(file.getOriginalFilename());
				dto.setUploadFileName(fileName);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		// 주석 todo test
		userService.signUp(dto);
		return "redirect:/user/sign-in";
	}

	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		// 1. 유효성 검사
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하시오", HttpStatus.BAD_REQUEST);
		}

		// 서비스 호출
		User principal = userService.signIn(dto);
		session.setAttribute(Define.PRINCIPAL, principal); // 세션에 저장

		System.out.println("principal" + principal.toString());

		return "redirect:/account/list";
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}

	// http://localhost:80/user/kakao-callback?code=EASDFEADSFAFDE
	@GetMapping("/kakao-callback")
	// @ResponseBody // 데이터를 반환하고 싶을 때
	public String kakaoCallBack(@RequestParam String code) {

		// 액세스 토큰 요청 --> Server to Server

		RestTemplate rt1 = new RestTemplate();

		// 헤더 구성
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body 구성
		MultiValueMap<String, String> params1 = new LinkedMultiValueMap();
		params1.add("grant_type", "authorization_code");
		params1.add("client_id", "레스트에이피아이 앱키");
		params1.add("redirect_uri", "http://localhost:80/user/kakao-callback");
		params1.add("code", code);

		// 헤더 + body 결합
		HttpEntity<MultiValueMap<String, String>> requestMsg1 = new HttpEntity<>(params1, headers1);

		// 요청 처리
		ResponseEntity<OAuthToken> response1 = rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				requestMsg1, OAuthToken.class);
		System.out.println("----------");
		System.out.println(response1.getHeaders());
		System.out.println(response1.getBody());
		System.out.println(response1.getBody().getAccessToken());
		System.out.println(response1.getBody().getRefreshToken());
		System.out.println("----------");
		// 여기까지 토큰 받기 위함

		RestTemplate rt2 = new RestTemplate();
		// 헤더 구성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + response1.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// 바디 구성 생략 (필수가 아님)
		// 헤더 바디 결합
		HttpEntity<MultiValueMap<String, String>> requestMsg2 = new HttpEntity<>(headers2);
		// 요청
		ResponseEntity<KakaoProfile> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				requestMsg2, KakaoProfile.class);

		System.out.println("----------");
		System.out.println(response2.getBody().getId());
		System.out.println(response2.getBody().getConnectedAt());
		System.out.println(response2.getBody().getProperties().getNickname());
		System.out.println("----------");

		// 카카오 서버에 존재하는 정보를 요청 처리
		System.out.println("----- 카카오 서버 정보 받기 완료 -----");

		// 1. 회원 가입 여부 확인
		// 최초 사요자라면 우리 사이트에 회원 가입을 자동 완료
		// 추가 정보 입력 화면 (추가 정보 있다면 기능을 만들기) --> DB 저장 처리

		KakaoProfile kakaoProfile = response2.getBody();
		// 소셜 회원 가입자는 전부 비번이 동일하게 된다.
		SignUpFormDto signUpFormDto = SignUpFormDto.
				builder()
				.username("OAuth_" + kakaoProfile.getId() + "_님")
				.fullname("Kakao").password("1234").file(null).originFileName(null).uploadFileName(null).build();

		User oldUser = userService.searchUsername(signUpFormDto.getUsername());
		if (oldUser == null) {
			// oldUser null 이라면 최초 회원 가입 처리를 해 주어야 한다.
			// 회원가입 자동 처리
			userService.signUp(signUpFormDto); // 회원가입 됨
			// 다시 사용자 정보 조회 처리
			oldUser = userService.searchUsername(signUpFormDto.getUsername());

		}

		// 만약 소셜 로그인 사용자가 회원가입 처리 완료된 사용자라면
		// 바로 세션 처리 및 로그인 처리
		oldUser.setPassword(null);
		session.setAttribute(Define.PRINCIPAL, oldUser);

		return "redirect:/account/list";
	}
}
