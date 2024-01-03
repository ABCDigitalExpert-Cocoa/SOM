package com.example.som.model.etc;

import javax.validation.constraints.NotBlank;


import lombok.Data;

@Data
public class EtcBoardWriteForm {
	
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	@NotBlank(message = "결과를 입력해 주세요.")
	private String results;
	
	public static EtcBoard toEtcBoard(EtcBoardWriteForm etcBoardWriteForm) {
		EtcBoard etcBoard = new EtcBoard();
		etcBoard.setTitle(etcBoardWriteForm.getTitle());
		etcBoard.setContent(etcBoardWriteForm.getContent());
		etcBoard.setResults(etcBoardWriteForm.getResults());
		
		return etcBoard;
	}
}
