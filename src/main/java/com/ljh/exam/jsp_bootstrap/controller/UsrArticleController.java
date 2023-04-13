package com.ljh.exam.jsp_bootstrap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.vo.Article;

@Controller

public class UsrArticleController {
	int articlesLastId;
	private List<Article> articles;

	public UsrArticleController() {
		articlesLastId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}

	private void makeTestData() {
		for(int i = 1; i<11; i++) {
			String title="제목"+i;
			String body="잘가"+i;
			int id = articlesLastId + i	;
			
			Article article = new Article(id,title,body);
			articles.add(article);
		}
		
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articlesLastId + 1;
		Article article = new Article(id, title, body);
		articles.add(article);
		articlesLastId = id;

		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;

	}

}
