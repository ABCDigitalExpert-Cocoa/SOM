package com.example.som.model.etc;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EtcBoardUpdateForm {
	private Long etc_id;
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	@NotBlank(message = "결과를 입력해 주세요.")
	private String results;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	private String saved_filename;
	private boolean fileRemoved;
}
