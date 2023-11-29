package com.tenco.bankapp.repository.entity;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Integer	id;
	private String username;
	private String password;
	private String fullname;
	private String originFileName;
	private String uploadFileName;
	private Time createdAt;
	
	public String setUpUserImage() {
		return uploadFileName == null ? "https://pucsum.photos/id/1/350" : "/images/uploads/" + uploadFileName;
	}
}
