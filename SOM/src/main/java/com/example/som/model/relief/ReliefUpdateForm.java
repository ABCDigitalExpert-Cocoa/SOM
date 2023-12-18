package com.example.som.model.relief;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReliefUpdateForm {
	private Long seq_id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private String mbti;
	private String stress_level;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private ReliefCategory relief_category;
	private boolean fileRemoved;
}
