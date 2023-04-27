package com.ljh.exam.jsp_bootstrap.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ljh.exam.jsp_bootstrap.vo.Reply;

@Mapper
public interface ReplyRepository {
	
	@Insert("""
			insert into reply
			set regDate = now(),
			updateDate = now(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`body` = #{body}
			""")
	void writeReply(int memberId, String relTypeCode, int relId, String body);
	
	@Select("""
			select LAST_INSERT_ID()
			""")
	int getLastInsertId();
	
	@Select("""
			select R.*,
			M.nickname AS extra__writerName
			from reply AS R
			left join `member` AS M
			on R.memberId = M.id
			where R.relTypeCode = #{relTypeCode}
			and R.relId = #{relId}
			order by R.id desc
			""")
	List<Reply> getForPrintReplies(int memberId, String relTypeCode, int relId);
	
	
}
