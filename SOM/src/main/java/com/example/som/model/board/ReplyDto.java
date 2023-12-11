package com.example.som.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyDto {
	private Long reply_id;
    private Long seq_id;
    private String member_id;
    private String content;
    private LocalDateTime create_date;
    private boolean isWriter;
}	
