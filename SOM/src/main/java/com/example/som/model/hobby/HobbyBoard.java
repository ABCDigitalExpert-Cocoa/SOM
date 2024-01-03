package com.example.som.model.hobby;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HobbyBoard {
		
	private Long hobby_id; //게시물 아이디
	private String title; //글 제목
	private String content; //내용
	private String price;
	private String region;
	private LocalDateTime create_date; //작성일
	private String hobby_category;
	private LocalDateTime update_date;
	private Long hit;
	private String saved_filename;
	
	public static HobbyBoardUpdateForm toBoardUpdateForm(HobbyBoard hobbyBoard) {
		HobbyBoardUpdateForm hobbyBoardUpdateForm = new HobbyBoardUpdateForm();
		hobbyBoardUpdateForm.setHobby_id(hobbyBoard.getHobby_id());
		hobbyBoardUpdateForm.setTitle(hobbyBoard.getTitle());
		hobbyBoardUpdateForm.setContent(hobbyBoard.getContent());
		hobbyBoardUpdateForm.setPrice(hobbyBoard.getPrice());
		hobbyBoardUpdateForm.setRegion(hobbyBoard.getRegion());
		hobbyBoardUpdateForm.setCreate_date(hobbyBoard.getCreate_date());
		hobbyBoardUpdateForm.setUpdate_date(hobbyBoard.getUpdate_date());
		hobbyBoardUpdateForm.setHit(hobbyBoard.getHit());
		hobbyBoardUpdateForm.setHobby_category(hobbyBoard.getHobby_category());
		return hobbyBoardUpdateForm;
	}
	
	public void addHit() {
		this.hit++;
	}
	
}
