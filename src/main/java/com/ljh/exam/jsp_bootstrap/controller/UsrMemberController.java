package com.ljh.exam.jsp_bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.MemberService;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	// 액션 메서드 시작
	@RequestMapping("/usr/member/dojoin")
	@ResponseBody
	 String dojoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		return "성공";
	}
}