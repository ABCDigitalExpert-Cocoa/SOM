package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.som.model.file.SavedFile;
import com.example.som.model.hobby.HobbyBoard;
import com.example.som.model.hobby.HobbyCategory;

@Mapper
public interface HobbyMapper {
	
	List<HobbyBoard> findBoards(HobbyCategory hobby_category, RowBounds rowBounds);

	void saveBoard(HobbyBoard hobbyBoard);
	
	HobbyBoard findBoardById(Long seq_id);

	void updateBoard(HobbyBoard hobbyBoard);

	void removeBoard(Long seq_id);

	SavedFile findFileByFileId(Long file_id);
	
	void saveBoardFile(SavedFile savedFile);

	SavedFile findFileBySeqId(Long seq_id);

	void removeSavedFile(Long file_id);
	
	int getTotal(HobbyCategory hobby_category);
}
