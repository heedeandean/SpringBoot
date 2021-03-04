package com.hand.springboot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	
	// http://localhost:3000/user
	@PostMapping("/user")
	public @ResponseBody String postMember(@RequestBody Map map) {
		
		JSONObject obj = new JSONObject();

		try {
			
			Member member = new Member(map.get("loginId").toString()
						 , map.get("password").toString()
						 , map.get("nickname").toString()
						 , "");
			
			userMap.put(member.getLoginId(), member);
			
			System.out.println("[USER CREATE!] " + member);
			obj.put("success", true);

		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
		}

		return obj.toJSONString();
	}
	

	// http://localhost:3000/login
	@PostMapping("/login")
	public @ResponseBody String loginMember(@RequestBody Map map) {
		
		JSONObject obj = new JSONObject();
		
		try {
			Member member = userMap.get(map.get("loginId"));

			if (member.getPassword().equals(map.get("password"))) {
				
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
