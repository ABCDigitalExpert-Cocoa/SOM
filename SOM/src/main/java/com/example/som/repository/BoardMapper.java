package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.file.SavedFile;

@Mapper
public interface BoardMapper {

	List<Board> findBoards(BoardCategory board_category);

	void saveBoard(Board board);
	
	Board findBoardById(Long seq_id);

	void updateBoard(Board board);

	void removeBoard(Long seq_id);

	SavedFile findFileByFileId(Long file_id);
	
	void saveBoardFile(SavedFile savedFile);

	SavedFile findFileBySeqId(Long seq_id);

	void removeSavedFile(Long file_id);

}
