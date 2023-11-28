package com.tenco.bankapp.dto;

import lombok.Data;

@Data
public class DepositFormDto {

	private Long amount;
	private String wAccountNumber;
	private String dAccountNumber;
	private String wAccountId;
	private String dAccountId;

}
