package com.tenco.bankapp.utils;

public class Define {

	public static final String PRINCIPAL = "principal";
	// 8bit -> 1byte
	// 1024byte -> 1KB 
	// 1048,767 -> 1MB
	// byte로 해야함.
	public static final int MAX_FILE_SIZE = 1024 * 1024 * 20; // 20MB
	public static final String UPLOAD_DIRECTORY = "C:\\spring_upload\\bank\\upload";
}
