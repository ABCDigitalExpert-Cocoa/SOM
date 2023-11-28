package com.example.som.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardUpdateForm {
	private String title;
	private String content;
	private LocalDateTime update_date;
}
