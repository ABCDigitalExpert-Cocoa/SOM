package com.example.som.model.relief;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReliefWriteForm {
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	private String mbti;
	private Long stress_level;
	private ReliefCategory relief_category;
	
	public static Relief toRelief(ReliefWriteForm reliefWriteForm) {
		Relief relief = new Relief();
		relief.setTitle(reliefWriteForm.getTitle());
		relief.setContent(reliefWriteForm.getContent());
		relief.setMbti(reliefWriteForm.getMbti());
		relief.setStress_level(reliefWriteForm.getStress_level());
		relief.setRelief_category(reliefWriteForm.getRelief_category());
		
		return relief;
	}
}
