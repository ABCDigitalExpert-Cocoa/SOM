package com.example.som.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardUpdateForm {
	private Long board_id;
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	private String member_id;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	private BoardCategory board_category;
	private boolean fileRemoved;
}
