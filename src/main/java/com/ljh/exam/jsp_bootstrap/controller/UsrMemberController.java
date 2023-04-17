package com.ljh.exam.jsp_bootstrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.MemberService;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Member;

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
	 Object dojoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		
	if(Ut.empty(loginId)) {
		return "loginId를(을) 입력해주세요.";
	}
	if(Ut.empty(loginPw)) {
		return "loginPw를(을) 입력해주세요.";
	}
	if(Ut.empty(name)) {
		return "name를(을) 입력해주세요.";
	}
	if(Ut.empty(nickname)) {
		return "nickname를(을) 입력해주세요.";
	}
	if(Ut.empty(cellphoneNo)) {
		return "cellphoneNo를(을) 입력해주세요.";
	}
	if(Ut.empty(email)) {
		return "email를(을) 입력해주세요.";
	}
	int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
	
	if ( id == -1 ) {
		return "해당 로그인 아이디는 이미 사용중입니다.";
	}
	if( id == -2) {
		return "가입하고자 하는 유저의 이름또는 이메일이 사용중인 상태입니다.";
	}
	Member member = memberService.getMemberById(id);
		
		return member;
	}
	
	@RequestMapping("/usr/member/getMembers")
	@ResponseBody
	public List<Member> getMembers() {
	List<Member> memberList=memberService.getMembers();
		return memberList;
	}
	
	@RequestMapping("/usr/member/getMemberById")
	@ResponseBody
	public Member getMemberById(int id) {
		Member member = memberService.getMemberById(id);
		return member;
	}
	
}