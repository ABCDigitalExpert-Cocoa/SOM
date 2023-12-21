package com.example.som.model.diary;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DiaryWriteForm {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private Emotion emotion;
	@NotNull
	private char open_or_not;
	
	public static Diary toDiary(DiaryWriteForm diaryWriteForm) {
		Diary diary = new Diary();
		diary.setTitle(diaryWriteForm.getTitle());
		diary.setContent(diaryWriteForm.getContent());
		diary.setEmotion(diaryWriteForm.getEmotion());
		diary.setOpen_or_not(diaryWriteForm.getOpen_or_not());
		
		return diary;
	}
}
