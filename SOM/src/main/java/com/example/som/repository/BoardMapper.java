package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;

@Mapper
public interface BoardMapper {

	List<Board> findBoards(BoardCategory board_category);

	void saveBoard(Board board);
	
	Board findBoardById(Long seq_id);

}
