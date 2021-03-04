package com.hand.springboot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	private Map<String, Member> userMap;

	@PostConstruct
	public void init() {
		userMap = new HashMap<String, Member>();
	}

	
	// http://localhost:3000/user?loginId=hello&password=world&nickname=handstudio
	@PostMapping("/user")
	public @ResponseBody String postMember(@RequestParam("loginId") String loginId
					     , @RequestParam("password") String password, @RequestParam("nickname") String nickname) {
		
		JSONObject obj = new JSONObject();

		try {
			Member member = new Member(loginId, password, nickname, "");
			userMap.put(loginId, member);

			System.out.println("USER CREATE! : " + userMap.get(loginId));
			obj.put("success", true);

		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
		}

		return obj.toJSONString();
	}
	

	// http://localhost:3000/login?loginId=hello&password=world
	@PostMapping("/login")
	public @ResponseBody String loginMember(@RequestParam("loginId") String loginId
					      , @RequestParam("password") String password) {
		
		JSONObject obj = new JSONObject();
		
		try {
			Member member = userMap.get(loginId);

			if (member.getPassword().equals(password)) {
				
				member.setToken("123456");

				obj.put("success", true);
				obj.put("token", member.getToken());
			} 
			else {
				obj.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
		}

		System.out.println(userMap);

		return obj.toJSONString();
	}

	
	// http://localhost:3000/user?token=123456
	@GetMapping("/user")
	public @ResponseBody String getMember(@RequestParam("token") String token) {
		
		JSONObject obj = new JSONObject();
		
		try {
			String loginId = null;
	
			for (Map.Entry<String, Member> entry : userMap.entrySet()) {
				if (entry.getValue().getToken().equals(token)) {
					loginId = entry.getKey();
				}
			}
			
			Member member = userMap.get(loginId);
			obj.put("loginId", member.getLoginId());
			obj.put("nickname", member.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
		}

		return obj.toJSONString();
	}
}
