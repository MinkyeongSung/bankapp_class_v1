package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;
	
	// 출금 기능
	public void withdraw(Long amount) {
		// 방어적 코드 작성 예정
		this.balance -= amount;
	}
	// 입금 기능
	public void deposit(Long amount) {
		this.balance += amount;
	}	
	
	// TODO - 추후 추가
	// 패스워드 체크기능
}
