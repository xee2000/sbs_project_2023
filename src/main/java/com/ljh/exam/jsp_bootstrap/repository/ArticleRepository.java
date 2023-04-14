package com.ljh.exam.jsp_bootstrap.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ljh.exam.jsp_bootstrap.vo.Article;

@Mapper
public interface ArticleRepository {
	
	@Select("SELECT * FROM article WHERE ID = #{id}")
	public Article getArticle(@Param("id") int id);
	
	@Select("SELECT * FROM article ORDER BY ID DESC")
	public List<Article> getArticles();

	@Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, `body` = #{body}")
	public void writeArticle(@Param("title")String title,@Param("body") String body);
	
	@Delete("DELETE FROM article WHERE ID = #{id}")
	public void deleteArticle(@Param("id")int id);

	@Update("UPDATE article SET title = #{title} , `body` = #{body}, updateDate = NOW() WHERE ID = #{id}")
	public void modifyArticle(@Param("id")int id, @Param("title")String title,@Param("body") String body);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

}