package com.example.som.model.board;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardWriteForm {
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	private BoardCategory board_category;
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setTitle(boardWriteForm.getTitle());
		board.setContent(boardWriteForm.getContent());
		board.setBoard_category(boardWriteForm.getBoard_category());
		
		return board;
	}
}
