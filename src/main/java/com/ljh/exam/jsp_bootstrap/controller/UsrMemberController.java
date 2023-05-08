package com.ljh.exam.jsp_bootstrap.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.MemberService;
import com.ljh.exam.jsp_bootstrap.utill.Ut;
import com.ljh.exam.jsp_bootstrap.vo.Member;
import com.ljh.exam.jsp_bootstrap.vo.ResultData;
import com.ljh.exam.jsp_bootstrap.vo.Rq;

@Controller
public class UsrMemberController {
	private MemberService memberService;
	private Rq rq;
	
	public UsrMemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		if ( Ut.empty(loginId) ) {
			return ResultData.from("F-A1", "loginId(을)를 입력해주세요.");
		}
		
		Member oldMember = memberService.getMemberByLoginId(loginId);
		
		if ( oldMember != null ) {
			return ResultData.from("F-A2", "해당 로그인 아이디는 이미 사용중입니다.", "loginId", loginId);
		}
		
		return ResultData.from("S-1", "사용 가능한 로그인 아이디입니다.", "loginId", loginId);
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email, @RequestParam(defaultValue = "/") String afterLoginUri) {
		if ( Ut.empty(loginId) ) {
			return rq.jsHistoryBack("F-1", "loginId(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(loginPw) ) {
			return rq.jsHistoryBack("F-2", "loginPw(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(name) ) {
			return rq.jsHistoryBack("F-3", "name(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(nickname) ) {
			return rq.jsHistoryBack("F-4", "nickname(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(cellphoneNo) ) {
			return rq.jsHistoryBack("F-5", "cellphoneNo(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(email) ) {
			return rq.jsHistoryBack("F-6", "email(을)를 입력해주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		if ( joinRd.isFail() ) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}
		
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getUriEncoded(afterLoginUri);
		
		return rq.jsReplace("회원가입이 완료되었습니다. 로그인 후 이용해주세요.", afterJoinUri);
	}
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue= "/") String afterLoginUri) {
		if ( !rq.isLogined() ) {
			return rq.jsHistoryBack("이미 로그아웃 상태입니다.");
		}
		
		rq.logout();
		
		return rq.jsReplace("로그아웃 되었습니다.", afterLoginUri);
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue= "/") String afterLoginUri) {
		if ( rq.isLogined() ) {
			return rq.jsHistoryBack("이미 로그인되었습니다.");
		}
		
		if ( Ut.empty(loginId) ) {
			return rq.jsHistoryBack("loginId(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(loginPw) ) {
			return rq.jsHistoryBack("loginPw(을)를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if ( member == null ) {
			return rq.jsHistoryBack("존재하지 않은 로그인아이디 입니다.");
		}
		
		if ( member.getLoginPw().equals(Ut.sha256(loginPw)) == false ) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		rq.login(member);
		
		return rq.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), afterLoginUri);
	}
	
	@RequestMapping("/usr/member/getMembers")
	@ResponseBody
	public List<Member> getMembers() {
		return memberService.getMembers();
	}
	
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		return "usr/member/myPage";
	}
	
	@RequestMapping("/usr/member/checkPassword")
	public String showCheckPassword() {
		return "usr/member/checkPassword";
	}
	
	@RequestMapping("/usr/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw, String replaceUri) {
		if ( rq.getLoginedMember().getLoginPw().equals(loginPw) == false ) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		if ( replaceUri.equals("../member/modify") ) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId());
			
			replaceUri += "?memberModifyAuthKey=" + memberModifyAuthKey;
		}
		
		return rq.jsReplace("", replaceUri);
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify(String memberModifyAuthKey) {
		if ( Ut.empty(memberModifyAuthKey) ) {
			return rq.historyBackJsOnview("memberModifyAuthKey가 필요합니다.");
		}
		
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(), memberModifyAuthKey);
		
		if ( checkMemberModifyAuthKeyRd.isFail() ) {
			return rq.historyBackJsOnview(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String memberModifyAuthKey, String loginPw, String name, String nickname, String email, String cellphoneNo) {
		if ( Ut.empty(memberModifyAuthKey) ) {
			return rq.historyBackJsOnview("memberModifyAuthKey가 필요합니다.");
		}
		
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(), memberModifyAuthKey);
		
		if ( checkMemberModifyAuthKeyRd.isFail() ) {
			return rq.historyBackJsOnview(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		if ( Ut.empty(loginPw) ) {
			loginPw = null;
		}
		
		if ( Ut.empty(name) ) {
			return rq.jsHistoryBack("이름(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(nickname) ) {
			return rq.jsHistoryBack("닉네임(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(email) ) {
			return rq.jsHistoryBack("이메일(을)를 입력해주세요.");
		}
		
		if ( Ut.empty(cellphoneNo) ) {
			return rq.jsHistoryBack("휴대전화번호(을)를 입력해주세요.");
		}
		
		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, email, cellphoneNo);
		
		return rq.jsReplace(modifyRd.getMsg(), "/");
	}
}