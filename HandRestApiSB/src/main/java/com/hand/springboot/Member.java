package com.hand.springboot;

import lombok.Data;

@Data
public class Member {
	private String loginId;
	private String password;
	private String nickname;
	private String token;
	
	public Member(String loginId, String password, String nickname, String token) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.token = token;
	}
}
