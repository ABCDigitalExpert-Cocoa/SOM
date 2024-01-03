package com.example.som.model.diary;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DiaryWriteForm {
	@NotBlank(message = "제목을 입력해 주세요.")
	private String title;
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;
	private Emotion emotion;
	@NotNull(message = "공개여부를 선택해 주세요.")
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
