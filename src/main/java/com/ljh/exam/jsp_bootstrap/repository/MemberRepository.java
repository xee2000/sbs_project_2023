package com.ljh.exam.jsp_bootstrap.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ljh.exam.jsp_bootstrap.vo.Member;

@Mapper
public interface MemberRepository {

	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNo = #{cellphoneNo},
			email = #{email};
			""")
 void join(@Param("loginId")String loginId,@Param("loginPw") String loginPw, 
			@Param("name")String name,@Param("nickname") String nickname, 
			@Param("cellphoneNo")String cellphoneNo,@Param("email") String email);
	
	@Select("SELECT * FROM MEMBER where authLevel=3 order by id desc")
	List<Member> selectMembers();

	@Select("select last_insert_id()")	
	int getLastInsertId();
	
	@Select("""
			select * from `member` as M
			where M.id = #{id}
			""")
	Member getMemberById(@Param("id") int id);

	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.loginId = #{loginId}
			""")
	Member getMemberByLoginId(@Param("loginId") String loginId);
}
