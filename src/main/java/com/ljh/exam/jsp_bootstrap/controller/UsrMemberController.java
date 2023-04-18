package com.ljh.exam.jsp_bootstrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.MemberService;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Member;
import com.ljh.exam.jsp_bootstrap.vo.ResultData;

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
	 public ResultData<Member> dojoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		
	if(Ut.empty(loginId)) {
		return ResultData.from("F-1", "loginId(을)를 입력해주세요");
	}
	if(Ut.empty(loginPw)) {
		return ResultData.from("F-2", "loginPw(을)를 입력해주세요");
	}
	if(Ut.empty(name)) {
		return ResultData.from("F-3", "name(을)를 입력해주세요");
	}
	if(Ut.empty(nickname)) {
		return ResultData.from("F-4", "nickname(을)를 입력해주세요");
	}
	if(Ut.empty(cellphoneNo)) {
		return ResultData.from("F-5", "cellphoneNo(을)를 입력해주세요");
	}
	if(Ut.empty(email)) {
		return ResultData.from("F-6", "email(을)를 입력해주세요");
	}
	ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
	
	if(joinRd.isFail()) {
		return (ResultData)joinRd;
	}
	Member member = memberService.getMemberById(joinRd.getData1());
		
		return ResultData.newData(joinRd, member);
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