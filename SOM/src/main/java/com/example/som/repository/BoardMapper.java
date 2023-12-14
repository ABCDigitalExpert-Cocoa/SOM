package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.file.SavedFile;

@Mapper
public interface BoardMapper {

	List<Board> findBoards(BoardCategory board_category, String searchText,  RowBounds rowBounds);

	void saveBoard(Board board);
	
	Board findBoardById(Long seq_id);

	void updateBoard(Board board);

	void removeBoard(Long seq_id);

	void addHit(Board board);
	
	int getTotal(BoardCategory board_category, String searchText);

}
