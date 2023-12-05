package com.example.som.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	private Long seq_id;
	private String title;
	private String content;
	private String member_id;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private Long hit;
	private BoardCategory board_category;
	
	public static BoardUpdateForm toBoardUpdateForm(Board board) {
		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
		boardUpdateForm.setSeq_id(board.getSeq_id());
		boardUpdateForm.setTitle(board.getTitle());
		boardUpdateForm.setContent(board.getContent());
		boardUpdateForm.setMember_id(board.getMember_id());
		boardUpdateForm.setCreate_date(board.getCreate_date());
		boardUpdateForm.setUpdate_date(board.getUpdate_date());
		boardUpdateForm.setHit(board.getHit());
		boardUpdateForm.setBoard_category(board.getBoard_category());
		return boardUpdateForm;
	}
	
	public void addHit() {
		this.hit++;
	}
}
