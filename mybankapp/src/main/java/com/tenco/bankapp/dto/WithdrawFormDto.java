package com.tenco.bankapp.dto;

import lombok.Data;

@Data
public class WithdrawFormDto {

	private Long amount;
	private String wAccountnumber;
	private String password;
}
