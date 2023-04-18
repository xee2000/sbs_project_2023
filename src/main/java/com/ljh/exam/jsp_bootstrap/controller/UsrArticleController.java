package com.ljh.exam.jsp_bootstrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.ArticleService;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Article;
import com.ljh.exam.jsp_bootstrap.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body);
		int id = writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(),article);
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData getArticles() {
		List<Article> articles = articleService.getArticles();
		return ResultData.from("S-1", "게시물 리스트입니다.",articles);
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article = articleService.getArticle(id);
		
		
		if ( article == null ) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.",id),article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {
		Article article = articleService.getArticle(id);
		
		if ( article == null ) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id),id);
		}
		
		articleService.deleteArticle(id);
		
		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id),id);
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		
		if ( article == null ) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id),id);
		}
		
		articleService.modifyArticle(id, title, body);
		
		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정하였습니다.", id),id);
	}
	// 액션 메서드 끝

}