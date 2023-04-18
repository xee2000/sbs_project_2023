package com.ljh.exam.jsp_bootstrap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ljh.exam.jsp_bootstrap.repository.ArticleRepository;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Article;
import com.ljh.exam.jsp_bootstrap.vo.ResultData;
@Service
public class ArticleService {
	private ArticleRepository articleRepository;
	
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}
	
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
	
	public ResultData writeArticle(String title, String body) {
		 articleRepository.writeArticle(title, body);
		 
		 int id = articleRepository.getLastInsertId();
		 return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id),id);
	}
	
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}
}