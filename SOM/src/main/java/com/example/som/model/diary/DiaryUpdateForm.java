package com.example.som.model.diary;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DiaryUpdateForm {
	private Long diary_id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private String emotion;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private String member_id;
	@NotNull
	private char open_or_not;
	private String saved_filename;
	private boolean fileRemoved;
}
