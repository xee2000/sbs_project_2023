package com.ljh.exam.jsp_bootstrap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ljh.exam.jsp_bootstrap.repository.MemberRepository;
import com.ljh.exam.jsp_bootstrap.vo.Member;


@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}



	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		Member oldMember = getMemberByLoginId(loginId);
		if ( oldMember != null ) {
			return -1;
		}
		oldMember = getMemberByNameandEmail(name, email);
		if(oldMember !=null) {
			return -2;
		}
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		return memberRepository.getLastInsertId();
	}
	  
	  private Member getMemberByLoginId(String loginId) {
		  
		  return memberRepository.getMemberByLoginId(loginId);
	}



	public Member getMemberById(int id) {
		  return memberRepository.getMemberById(id);
	  }
	
	public Member getMemberByNameandEmail(String name, String email) {
		  return memberRepository.getMemberByNameandEmail(name, email);
	  }
	  public List<Member> getMembers(){
		  List<Member> memberList =memberRepository.selectMembers();
		  return memberList;
	  }
	  
	  


}
