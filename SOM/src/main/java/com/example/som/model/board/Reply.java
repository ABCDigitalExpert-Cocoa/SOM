package com.example.som.model.board;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Reply {
	private Long reply_id;		// 리플 아이디(일련번호)
	private String content;		// 리플 내용
	private String member_id;	// 작성자 아이디
	private Long seq_id;		// 게시글 아이디
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime create_date;	// 등록시간
	
	public static ReplyDto toDto(Reply reply) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setReply_id(reply.getReply_id());
        replyDto.setContent(reply.getContent());
        replyDto.setMember_id(reply.getMember_id());
        replyDto.setSeq_id(reply.getSeq_id());
        replyDto.setCreate_date(reply.getCreate_date());

        return replyDto;
    }
}	
