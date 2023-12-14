package com.example.som.model.diary;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Diary {
	private Long diary_id;
	private String title;
	private String content;
	private String emotion;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private String member_id;
	private char open_or_not;
	private String saved_filename;
	
	public static DiaryUpdateForm toDiaryUpdateForm(Diary diary) {
		DiaryUpdateForm diaryUpdateForm = new DiaryUpdateForm();
		diaryUpdateForm.setDiary_id(diary.getDiary_id());
		diaryUpdateForm.setTitle(diary.getTitle());
		diaryUpdateForm.setContent(diary.getContent());
		diaryUpdateForm.setEmotion(diary.getEmotion());
		diaryUpdateForm.setCreate_date(diary.getCreate_date());
		diaryUpdateForm.setMember_id(diary.getMember_id());
		diaryUpdateForm.setOpen_or_not(diary.getOpen_or_not());
		diaryUpdateForm.setSaved_filename(diary.getSaved_filename());
		
		return diaryUpdateForm;
	}
}
