package com.example.som.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.file.SavedFile;
import com.example.som.repository.BoardMapper;
import com.example.som.util.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final FileService fileService;
	
	public List<Board> findBoards(BoardCategory board_category) {
		
		return boardMapper.findBoards(board_category);
	}
	
	@Transactional
	public void saveBoard(Board board, MultipartFile file) {
		boardMapper.saveBoard(board);
		
		if(file != null && file.getSize() > 0) {
			SavedFile savedFile = fileService.saveFile(file);
			savedFile.setSeq_id(board.getSeq_id());
			boardMapper.saveBoardFile(savedFile);
		}
	}
	
	public Board findBoardById(Long seq_id) {
		return boardMapper.findBoardById(seq_id);
	}
	
	@Transactional
	public void updateBoard(Board board) {
		boardMapper.updateBoard(board);
	}

	public void removeBoard(Long seq_id) {
		boardMapper.removeBoard(seq_id);
	}

	public SavedFile findFileByFileId(Long file_id) {
		return boardMapper.findFileByFileId(file_id);
	}

	public SavedFile findFileBySeqId(Long seq_id) {
		return boardMapper.findFileBySeqId(seq_id);
	}

}
