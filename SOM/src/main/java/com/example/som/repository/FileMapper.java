package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.file.SavedFile;

@Mapper
public interface FileMapper {
	SavedFile findFileByFileId(Long file_id);
	
	void saveBoardFile(SavedFile savedFile);

	SavedFile findFileBySeqId(Long seq_id);

	void removeSavedFile(Long file_id);
	
	void saveDiaryFile(SavedFile savedFile);
}
