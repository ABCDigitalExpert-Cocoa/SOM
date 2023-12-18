package com.example.som.model.hobby;

import java.time.LocalDateTime;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.board.BoardUpdateForm;

import lombok.Data;

@Data
public class HobbyBoard {
		
	private Long seq_id; //게시물 아이디
	private String title; //글 제목
	private String content; //내용
	private String region;
	private LocalDateTime create_date; //작성일
	private String member_id;
	private HobbyCategory hobby_category;
	private LocalDateTime update_date;
	private Long hit;
	
	public static HobbyBoardUpdateForm toBoardUpdateForm(HobbyBoard hobbyBoard) {
		HobbyBoardUpdateForm hobbyBoardUpdateForm = new HobbyBoardUpdateForm();
		hobbyBoardUpdateForm.setSeq_id(hobbyBoard.getSeq_id());
		hobbyBoardUpdateForm.setTitle(hobbyBoard.getTitle());
		hobbyBoardUpdateForm.setContent(hobbyBoard.getContent());
		hobbyBoardUpdateForm.setMember_id(hobbyBoard.getMember_id());
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
