package com.example.som.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.diary.Diary;
import com.example.som.model.file.SavedFile;
import com.example.som.repository.DiaryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
	
	private final DiaryMapper diaryMapper;
	private final SavedFileService savedFileService;
	
	@Transactional
	public void saveDiary(Diary diary, MultipartFile file) {
		diaryMapper.saveDiary(diary);
		
		if(file != null && file.getSize() > 0) {
			savedFileService.saveDiaryFile(file, diary.getDiary_id());
		}
	}
	
	public List<Diary> findMyDiary(String member_id, int startRecord, int countPerPage){
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		
		return diaryMapper.findMyDiary(member_id, rowBounds);
	}
	
	public Diary findDiary(Long diary_id) {
		return diaryMapper.findDiary(diary_id);
	}
	
	@Transactional
	public void updateDiary(Diary updateDiary, boolean isFileRemoved, MultipartFile file) {
		Diary diary = findDiary(updateDiary.getDiary_id());
		if(diary != null) {
			diaryMapper.updateDiary(updateDiary);
			SavedFile savedFile = savedFileService.findFileByDiaryId(updateDiary.getDiary_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				savedFileService.removeSavedFile(savedFile.getFile_id());
			}
			if(file != null && file.getSize() > 0) {
				savedFileService.saveDiaryFile(file, diary.getDiary_id());
			}
		}
	}

	public int getTotal(String member_id) {
		return diaryMapper.getTotal(member_id);
	}
	
	@Transactional
	public void removeDiary(Long diary_id) {
		SavedFile savedFile = savedFileService.findFileByDiaryId(diary_id);
		if(savedFile != null) {
			savedFileService.removeSavedFile(savedFile.getFile_id());;
		}
		diaryMapper.removeDiary(diary_id);
	}
	
	public List<Diary> findAllOpenDiary(){
		return diaryMapper.findAllOpenDiary();
	}
	
	
}
