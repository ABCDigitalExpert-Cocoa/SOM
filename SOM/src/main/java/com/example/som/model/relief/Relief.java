package com.example.som.model.relief;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class Relief {
	private Long seq_id;
	private String title;
	private String content;
	private Long stress_level;
	private String mbti;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private ReliefCategory relief_category;
	
	public static ReliefUpdateForm toReliefUpdateForm(Relief relief) {
		ReliefUpdateForm reliefUpdateForm = new ReliefUpdateForm();
		reliefUpdateForm.setSeq_id(relief.getSeq_id());
		reliefUpdateForm.setTitle(relief.getTitle());
		reliefUpdateForm.setContent(relief.getContent());
		reliefUpdateForm.setCreate_date(relief.getCreate_date());
		reliefUpdateForm.setUpdate_date(relief.getUpdate_date());
		reliefUpdateForm.setRelief_category(relief.getRelief_category());
		return reliefUpdateForm;
	}
	
}
