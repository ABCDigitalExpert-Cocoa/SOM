package com.example.som.model.hobby;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.som.model.board.BoardCategory;
import com.example.som.model.board.BoardUpdateForm;

import lombok.Data;

@Data
public class HobbyBoardUpdateForm {
	private Long hobby_id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private String region;
	private String member_id;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	private HobbyCategory hobby_category;
	private boolean fileRemoved;
}
