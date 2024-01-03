package com.example.som.model.reply;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReplyDto {
	private Long reply_id;
    private Long board_id;
    private String member_id;
    private String nickname;
    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime create_date;
    private boolean isWriter;
}	
