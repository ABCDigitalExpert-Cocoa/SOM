package com.example.som.model.reply;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReplyDto {
	private Long reply_id;
    private Long board_id;
    private String member_id;
    private String nickname;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime create_date;
    private boolean isWriter;
}	
