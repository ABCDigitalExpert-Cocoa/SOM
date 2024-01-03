package com.example.som.model.hobby;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.example.som.model.board.BoardCategory;
import com.example.som.model.board.BoardUpdateForm;

import lombok.Data;

@Data
public class HobbyBoardUpdateForm {
	private Long hobby_id;
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	private String price;
	@NotBlank(message = "지역을 선택해 주세요.")
	private String region;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	@NotBlank(message = "카테고리를 선택해 주세요.")
	private String hobby_category;
	private boolean fileRemoved;
}
