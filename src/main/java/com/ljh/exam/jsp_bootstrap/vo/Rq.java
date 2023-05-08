package com.ljh.exam.jsp_bootstrap.vo;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.ljh.exam.jsp_bootstrap.service.MemberService;
import com.ljh.exam.jsp_bootstrap.utill.Ut;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap; 
	
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		
		paramMap = Ut.getParamMap(req);
		
		this.session = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if ( session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		
		this.req.setAttribute("rq", this);
	}
	
	public boolean isNotLogined() {
		return !isLogined;
	}
	
	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}
	
	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	public String historyBackJsOnview(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/js";
	}
	
	public String historyBackJsOnview(String resultCode, String msg) {
		req.setAttribute("msg", String.format("[%s]%s ", resultCode,msg));
		req.setAttribute("historyBack", true);
		return "common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}
	
	public String jsHistoryBack(String resultCode, String msg) {
		msg = String.format("[%s] %s", resultCode, msg);
		return Ut.jsHistoryBack(msg);
	}
	
	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		
		if (queryString != null && queryString.length() > 0 ) {
			currentUri += "?" + queryString;
		}
		
		return currentUri;
	}
	
	public String getEncodedCurrentUri() {
		return Ut.getUriEncoded(getCurrentUri());
	}

	// 이 메서드는 Rq객체가 자연스럽게 생성되도록 유도하는 역할을 한다.
	// 지우면 안되고,
	// 편의를 위해 BeforeActionInterceptor에서 꼭 호출해줘야 한다.
	public void initOnBeforeActionInterceptor() {
	}

	public void printReplaceJs(String msg, String uri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, uri));
	}

	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}

	public String getAfterLoginUri() {
		String requestUri = req.getRequestURI(); 
		
		// 로그인 후 돌아가면 안되는 페이지 URL 들을 적으시면 됩니다.
		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
		case "/usr/member/findLoginId":
		case "/usr/member/findLoginPw":
			return Ut.getUriEncoded(Ut.getStrAttr(paramMap, "afterLoginUri", ""));
		}
		
		return getEncodedCurrentUri();
	}
	
	public String getLogoutUri() {
		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}

	public String getAfterLogoutUri() {
		String requestUri = req.getRequestURI(); 
		
//		switch (requestUri) {
//		case "/usr/article/write":
//			return "";
//		}
		
		return getEncodedCurrentUri();
	}
	
	public String getArticleDetailUriFromArticleList(Article article) {
		return "../article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}
	
	public String getJoinUri() {
		return "../member/join?afterLogoutUri=" + getAfterLogoutUri();
	}
}