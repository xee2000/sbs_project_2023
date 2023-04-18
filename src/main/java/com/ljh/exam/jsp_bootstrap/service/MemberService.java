package com.ljh.exam.jsp_bootstrap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ljh.exam.jsp_bootstrap.repository.MemberRepository;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Member;
import com.ljh.exam.jsp_bootstrap.vo.ResultData;


@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}



	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		Member oldMember = getMemberByLoginId(loginId);
		if ( oldMember != null ) {
			return ResultData.from("F-7", Ut.f("해당 로그인 아이디(%s)는 이미 사용중입니다.", loginId));
		}
		oldMember = getMemberByNameandEmail(name, email);
		if(oldMember !=null) {
			return ResultData.from("F-8", Ut.f("해당 이름(%s)과 이메일(%s)는 이미 사용중입니다.", name,email));
		}
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		int id = memberRepository.getLastInsertId();
		return ResultData.from("S-1", "회원가입이 완료되었습니다.",id);
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
