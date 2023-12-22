package com.example.som.model.etc;

import javax.validation.constraints.NotBlank;


import lombok.Data;

@Data
public class EtcBoardWriteForm {
	
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	
	public static EtcBoard toEtcBoard(EtcBoardWriteForm etcBoardWriteForm) {
		EtcBoard etcBoard = new EtcBoard();
		etcBoard.setTitle(etcBoardWriteForm.getTitle());
		etcBoard.setContent(etcBoardWriteForm.getContent());
		
		return etcBoard;
	}
}