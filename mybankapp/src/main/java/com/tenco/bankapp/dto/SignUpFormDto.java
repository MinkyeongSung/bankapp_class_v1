package com.tenco.bankapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpFormDto {

	private String username;
	private String password;
	private String fullname;

}
