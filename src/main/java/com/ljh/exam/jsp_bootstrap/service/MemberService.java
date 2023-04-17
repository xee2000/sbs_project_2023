package com.ljh.exam.jsp_bootstrap.service;

import java.util.List;

import com.ljh.exam.jsp_bootstrap.repository.MemberRepository;
import com.ljh.exam.jsp_bootstrap.vo.Member;

public class MemberService {
private MemberRepository memberRepository;
	

public MemberService(MemberRepository memberRepository) {
	this.memberRepository = memberRepository;
}
public Member getMember(int id) {
	return memberRepository.selectMember(id);
}
	
	public List<Member> getMembers() {
		return memberRepository.selectListMember();
	}
	
	public void deleteArticle(int id) {
		memberRepository.deleteMember(id);
	}

	public void modifyMember(Member member) {
		memberRepository.updateMember(member);
	}
	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		// TODO Auto-generated method stub
		
	}
}
