package com.example.som.model.etc;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EtcBoard {

	private Long etc_id; //게시물 아이디
	private String title; //글 제목
	private String content; //내용
	private String results; //내용
	private LocalDateTime create_date; //작성일
	private LocalDateTime update_date;
	private Long hit;
	private String saved_filename;
	
	public static EtcBoardUpdateForm toEtcBoardUpdateForm(EtcBoard etcBoard) {
		EtcBoardUpdateForm etcBoardUpdateForm = new EtcBoardUpdateForm();
		etcBoardUpdateForm.setEtc_id(etcBoard.getEtc_id());
		etcBoardUpdateForm.setTitle(etcBoard.getTitle());
		etcBoardUpdateForm.setContent(etcBoard.getContent());
		etcBoardUpdateForm.setResults(etcBoard.getResults());
		etcBoardUpdateForm.setCreate_date(etcBoard.getCreate_date());
		etcBoardUpdateForm.setUpdate_date(etcBoard.getUpdate_date());
		etcBoardUpdateForm.setHit(etcBoard.getHit());
		etcBoardUpdateForm.setSaved_filename(etcBoard.getSaved_filename());
		return etcBoardUpdateForm;
	}
	
	public void addHit() {
		this.hit++;
	}
}
