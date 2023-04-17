package com.ljh.exam.jsp_bootstrap.repository;

import java.util.List;

import com.ljh.exam.jsp_bootstrap.vo.Member;

public interface MemberRepository {
	
	List<Member> selectListMember();
	
	Member selectMember(int id);
	
	void insertMember(Member member);
	
	void updateMember(Member member);
	
	void deleteMember(int id);
}
