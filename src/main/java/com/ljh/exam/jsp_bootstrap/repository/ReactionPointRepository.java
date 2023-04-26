package com.ljh.exam.jsp_bootstrap.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {
	@Select("""
			SELECT IFNULL(SUM(RP.point), 0) AS s
			FROM reactionPoint AS RP
			WHERE RP.relTypeCode = 'article'
			AND RP.relId = #{relId}
			AND RP.memberId = #{memberId}
			""")
	public int actorCanMakeReactionPoint(int memberId, String relTypeCode, int relId);

	@Insert("""
			insert into reactionPoint
			set regDate = now(),
			updateDate = now(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{memberId},
			`point` = 1
			""")
	public void addGoodReactionPoint(int memberId, String relTypeCode, int relId);
	
	@Insert("""
			insert into reactionPoint
			set regDate = now(),
			updateDate = now(),
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			memberId = #{memberId},
			`point` = -1
			""")
	public void addBadReactionPoint(int memberId, String relTypeCode, int relId);
}
