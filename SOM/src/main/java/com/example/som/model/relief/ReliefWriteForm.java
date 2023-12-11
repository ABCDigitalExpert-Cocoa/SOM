package com.example.som.model.relief;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReliefWriteForm {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private ReliefCategory relief_category;
	
	public static Relief toRelief(ReliefWriteForm reliefWriteForm) {
		Relief relief = new Relief();
		relief.setTitle(reliefWriteForm.getTitle());
		relief.setContent(reliefWriteForm.getContent());
		relief.setRelief_category(reliefWriteForm.getRelief_category());
		
		return relief;
	}
}
