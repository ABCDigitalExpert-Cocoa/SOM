package com.example.som.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.diary.Diary;
import com.example.som.model.file.SavedFile;
import com.example.som.repository.DiaryMapper;
import com.example.som.repository.FileMapper;
import com.example.som.util.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
	
	private final DiaryMapper diaryMapper;
	private final FileMapper fileMapper;
	private final FileService fileService;
	@Value("${file.upload.path}")
	private String uploadPath;
	
	public void saveDiary(Diary diary, MultipartFile file) {
		diaryMapper.saveDiary(diary);
		
		if(file != null && file.getSize() > 0) {
			SavedFile savedFile = fileService.saveFile(file);
			savedFile.setDiary_id(diary.getDiary_id());
			fileMapper.saveDiaryFile(savedFile);
		}
	}
	
	public List<Diary> findAllDiary(String member_id){
		return diaryMapper.findAllDiary(member_id);
	}
	

}
