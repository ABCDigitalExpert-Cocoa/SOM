package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.reply.Reply;

@Mapper
public interface ReplyMapper {
	// 리플 등록
	void saveReply(Reply reply);
	
	// 리플 읽기
	Reply findReply(Long reply_id);
	
	// 리플 목록
	List<Reply> findReplies(Long board_id);
	
	// 리플 수정
	void updateReply(Reply reply);
	
	// 리플 삭제
	void removeReply(Long reply_id);
	
	// 게시물 삭제
	void removeAllReply(Long board_id);
}









