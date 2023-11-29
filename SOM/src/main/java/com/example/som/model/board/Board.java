package com.example.som.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	private Long seq_id;
	private String title;
	private String content;
	private String member_id;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	private BoardCategory board_category;
	
	public void addHit() {
		this.hit++;
	}
}
