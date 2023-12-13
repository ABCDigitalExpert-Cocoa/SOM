package com.example.som.model.relief;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReliefUpdateForm {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private ReliefCategory relief_category;
	private boolean fileRemoved;
}
