package com.example.som.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.repository.BoardMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {
	
	private final BoardMapper boardMapper;
	
	public List<Board> findBoards(BoardCategory board_category) {
		
		return boardMapper.findBoards(board_category);
	}
	
	@Transactional
	public void saveBoard(Board board) {
		boardMapper.saveBoard(board);
	}
	
	public Board findBoardById(Long seq_id) {
		return boardMapper.findBoardById(seq_id);
	}

	public void updateBoard(Board board) {
		boardMapper.updateBoard(board);
	}

	public void removeBoard(Long seq_id) {
		boardMapper.removeBoard(seq_id);
	}

}
