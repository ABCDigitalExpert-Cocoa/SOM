package com.example.som.model.etc;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EtcBoardUpdateForm {
	private Long etc_id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	private String saved_filename;
	private boolean fileRemoved;
}
