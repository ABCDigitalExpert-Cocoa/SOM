package com.example.som.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.file.SavedFile;

@Mapper
public interface FileMapper {
	SavedFile findFileByFileId(Long file_id);
	
	void saveBoardFile(SavedFile savedFile);

	SavedFile findBoardFile(Long board_id);

	void removeSavedFile(Long file_id);
	
	void saveDiaryFile(SavedFile savedFile);
	
	void saveReliefFile(SavedFile savedFile);

	SavedFile findDiaryFile(Long diary_id);

	SavedFile findReliefFile(Long relief_id);
}
