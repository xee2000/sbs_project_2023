package com.ljh.exam.jsp_bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.exam.jsp_bootstrap.service.ReactionPointService;
import com.ljh.exam.jsp_bootstrap.vo.Rq;

@Controller
public class UsrReactionPointController {

		private ReactionPointService reactionPointService;
		private Rq rq;
		
		public UsrReactionPointController(ReactionPointService reactionPointService, Rq rq) {
			this.reactionPointService = reactionPointService;
			this.rq = rq;
		}
		
		@RequestMapping("/usr/reactionPoint/doGoodReaction")
		@ResponseBody
		String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
			boolean actorCanMakeReactionPoint = reactionPointService.actorCanMakeReactionPoint(relId, relTypeCode, relId);
			if(actorCanMakeReactionPoint == false) {
				return rq.jsHistoryBack("이미 처리되었습니다.");
			}
			reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(),relTypeCode,relId);
			return rq.jsReplace("좋아요를 하셨습니다.", replaceUri);
		}
		
		@RequestMapping("/usr/reactionPoint/doBadReaction")
		@ResponseBody
		String doBaddReaction(String relTypeCode, int relId, String replaceUri) {
			boolean actorCanMakeReactionPoint = reactionPointService.actorCanMakeReactionPoint(relId, relTypeCode, relId);
			if(actorCanMakeReactionPoint == false) {
				return rq.jsHistoryBack("이미 처리되었습니다.");
			}
			reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(),relTypeCode,relId);
			return rq.jsReplace("싫어요를 하셨습니다.", replaceUri);
		}
		
		
}
