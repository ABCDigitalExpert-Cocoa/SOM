package com.example.som.model.hobby;

import javax.validation.constraints.NotBlank;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.board.BoardWriteForm;

import lombok.Data;

@Data
public class HobbyBoardWriteForm {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private String region;
	private HobbyCategory hobby_category;
	
	public static HobbyBoard toHobbyBoard(HobbyBoardWriteForm hobbyBoardWriteForm) {
		HobbyBoard hobbyBoard = new HobbyBoard();
		hobbyBoard.setTitle(hobbyBoardWriteForm.getTitle());
		hobbyBoard.setContent(hobbyBoardWriteForm.getContent());
		hobbyBoard.setRegion(hobbyBoardWriteForm.getRegion());
		hobbyBoard.setHobby_category(hobbyBoardWriteForm.getHobby_category());
		
		return hobbyBoard;
	}
}
