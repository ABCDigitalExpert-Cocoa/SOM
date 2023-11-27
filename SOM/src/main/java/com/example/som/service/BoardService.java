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

}
