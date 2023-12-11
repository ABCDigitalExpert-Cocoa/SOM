package com.example.som.model.diary;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DiaryWriteForm {
	private String title;
	private String content;
	private String emotion;
	private LocalDateTime create_date;
	private String member_id;
	private char open_or_not;
}
