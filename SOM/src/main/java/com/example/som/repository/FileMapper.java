package com.example.som.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.file.SavedFile;

@Mapper
public interface FileMapper {
	SavedFile findFileByFileId(Long file_id);
	
	void saveBoardFile(SavedFile savedFile);

	void saveDiaryFile(SavedFile savedFile);
	
	void saveReliefFile(SavedFile savedFile);
	
	void saveHobbyFile(SavedFile savedFile);
	
	SavedFile findBoardFile(Long board_id);

	SavedFile findDiaryFile(Long diary_id);
	
	SavedFile findReliefFile(Long relief_id);
	
	SavedFile findHobbyFile(Long hobby_id);
	
	void removeSavedFile(Long file_id);
	
	SavedFile findEtcFile(Long etc_id);

	void saveEtcFile(SavedFile savedFile);
}
