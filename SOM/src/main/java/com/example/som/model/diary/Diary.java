package com.example.som.model.diary;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Diary {
	private Long diary_id;
	private String title;
	private String content;
	private String emotion;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private String member_id;
	private char open_or_not;
}
