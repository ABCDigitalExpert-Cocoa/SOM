package com.example.som.model.hobby;

import javax.validation.constraints.NotBlank;


import lombok.Data;

@Data
public class HobbyBoardWriteForm {
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	@NotBlank(message = "지역을 선택해 주세요.")
	private String region;
	private String price;
	@NotBlank(message = "카테고리를 선택해 주세요.")
	private String hobby_category;
	
	public static HobbyBoard toHobbyBoard(HobbyBoardWriteForm hobbyBoardWriteForm) {
		HobbyBoard hobbyBoard = new HobbyBoard();
		hobbyBoard.setTitle(hobbyBoardWriteForm.getTitle());
		hobbyBoard.setContent(hobbyBoardWriteForm.getContent());
		hobbyBoard.setRegion(hobbyBoardWriteForm.getRegion());
		hobbyBoard.setPrice(hobbyBoardWriteForm.getPrice());
		hobbyBoard.setHobby_category(hobbyBoardWriteForm.getHobby_category());
		
		return hobbyBoard;
	}
}
