package com.tenco.bankapp.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

// {"access_token":"6ZsUWB04tsmIAPUbpR9pn5EOFsAfTBAXOSMKKw0gAAABjB4Nre8WphHJzwXJqw","token_type":"bearer","refresh_token":"Kv-WgR3iovegJ9hBeO7ec1CYpepjrygOge0KKw0gAAABjB4NresWphHJzwXJqw","expires_in":21599,"scope":"profile_image profile_nickname","refresh_token_expires_in":5183999}

@Data
// JSON형식의 코딩 컨벤션의 스네이크 케이스를 자바 카멜 노테이션으로 변환 처리
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthToken {
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private String expiresIn;
	private String scope;
}
