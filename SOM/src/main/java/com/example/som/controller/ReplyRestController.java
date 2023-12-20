package com.example.som.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.member.Member;
import com.example.som.model.reply.Reply;
import com.example.som.model.reply.ReplyDto;
import com.example.som.repository.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("reply")
@RestController
public class ReplyRestController {
	
	private final ReplyMapper replyMapper;
	
	// 리플 등록
	@PostMapping("{board_id}")
	public ResponseEntity<String> writeReply(@PathVariable Long board_id,
											 @ModelAttribute Reply reply,
											 @AuthenticationPrincipal PrincipalDetails userInfo) {
		log.info("reply: {}", reply);
		reply.setBoard_id(board_id);
		reply.setMember_id(userInfo.getUsername());
		
		replyMapper.saveReply(reply);
		
		return ResponseEntity.ok("등록 성공");
	}
	
	// 리플 목록
	@GetMapping("{board_id}")
	public ResponseEntity<List<ReplyDto>> findReplies(@AuthenticationPrincipal PrincipalDetails userInfo,
												   	  @PathVariable Long board_id) {
		List<Reply> replies = replyMapper.findReplies(board_id);
		List<ReplyDto> replyDtos = new ArrayList<>();
        if (replies != null && replies.size() > 0) {
            for (Reply reply : replies) {
                ReplyDto replyDto = Reply.toDto(reply);
                if (reply.getMember_id().equals(userInfo.getUsername())) {
                    replyDto.setWriter(true);
                }
                replyDtos.add(replyDto);
            }
        }
		
		return ResponseEntity.ok(replyDtos);
	}
	
	// 리플 수정
	@PutMapping("{board_id}/{reply_id}")
	public ResponseEntity<Reply> updateReply(@AuthenticationPrincipal PrincipalDetails userInfo,
											 @PathVariable Long board_id,
											 @PathVariable Long reply_id,
											 @ModelAttribute Reply reply) {
		log.info("reply: {}", reply);
		
		// 수정 권한 확인
        Reply findReply = replyMapper.findReply(reply_id);
        if (findReply.getMember_id().equals(userInfo.getUsername())) {
            findReply.setContent(reply.getContent());
        	replyMapper.updateReply(reply);
        }
		
		return ResponseEntity.ok(reply);
	}
	
	// 리플 삭제
	@DeleteMapping("{board_id}/{reply_id}")
	public ResponseEntity<String> removeReply(@AuthenticationPrincipal PrincipalDetails userInfo,
											  @PathVariable Long board_id,
											  @PathVariable Long reply_id) {
		 // 삭제 권한 확인
        Reply findReply = replyMapper.findReply(reply_id);
        if (findReply.getMember_id().equals(userInfo.getUsername())) {
            replyMapper.removeReply(reply_id);
        }
		return ResponseEntity.ok("삭제 성공");
	}
}










