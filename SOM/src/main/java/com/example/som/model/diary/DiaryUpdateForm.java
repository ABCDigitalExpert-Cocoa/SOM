package com.example.som.model.diary;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DiaryUpdateForm {
	private Long diary_id;
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	private Emotion emotion;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private String member_id;
	@NotNull(message = "공개여부를 선택해 주세요.")
	private char open_or_not;
	private String saved_filename;
	private boolean fileRemoved;
}
