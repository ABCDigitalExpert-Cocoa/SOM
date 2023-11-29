package com.example.som.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardUpdateForm {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private LocalDateTime update_date;
}
