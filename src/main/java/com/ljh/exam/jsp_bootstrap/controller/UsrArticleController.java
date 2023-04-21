package com.ljh.exam.jsp_bootstrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.ArticleService;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Article;
import com.ljh.exam.jsp_bootstrap.vo.ResultData;
import com.ljh.exam.jsp_bootstrap.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {
		Rq rq = (Rq)req.getAttribute("rq");
				
		if ( Ut.empty(title) ) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(body) ) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		
		int id = writeArticleRd.getData1();
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "article", article);
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite() {
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);

		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpServletRequest req, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		if ( rq.isLogined() == false ) {
			return Ut.jsHistoryBack("로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		if ( article.getMemberId() != rq.getLoginedMemberId() ) {
			return Ut.jsHistoryBack("권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		
		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
	}
	
	@RequestMapping("/usr/article/modify")
	public String ShowModify(HttpServletRequest req, Model model, int id, String title, String body) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if ( actorCanModifyRd.isFail() ) {
			return rq.historyBackJsOnview(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		if ( rq.isLogined() == false ) {
			return Ut.jsHistoryBack("로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if ( article == null ) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if ( actorCanModifyRd.isFail() ) {
			return Ut.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return Ut.jsReplace(Ut.f("%d번 글이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	// 액션 메서드 끝

}
