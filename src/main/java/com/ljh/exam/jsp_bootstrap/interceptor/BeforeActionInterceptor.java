package com.ljh.exam.jsp_bootstrap.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ljh.exam.jsp_bootstrap.service.MemberService;
import com.ljh.exam.jsp_bootstrap.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor{
	private Rq rq;
	public BeforeActionInterceptor(Rq rq) {
		this.rq=rq;
	}
	@Autowired
	private MemberService memberService;
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		rq.initOnBeforeActionInterceptor();
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
