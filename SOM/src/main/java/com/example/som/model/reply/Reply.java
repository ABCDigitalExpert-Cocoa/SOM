package com.example.som.model.reply;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Reply {
	private Long reply_id;		// 리플 아이디(일련번호)
	private String content;		// 리플 내용
	private String member_id;	// 작성자 아이디
	private String nickname;	// 작성자 닉네임
	private Long board_id;		// 게시글 아이디
	private LocalDateTime create_date;	// 등록시간
	
	public static ReplyDto toDto(Reply reply) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setReply_id(reply.getReply_id());
        replyDto.setContent(reply.getContent());
        replyDto.setMember_id(reply.getMember_id());
        replyDto.setNickname(reply.getNickname());
        replyDto.setBoard_id(reply.getBoard_id());
        replyDto.setCreate_date(reply.getCreate_date());

        return replyDto;
    }
}	
