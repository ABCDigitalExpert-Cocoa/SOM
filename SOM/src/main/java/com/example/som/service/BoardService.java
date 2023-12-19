package com.example.som.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.board.Board;
import com.example.som.model.board.BoardCategory;
import com.example.som.model.file.SavedFile;
import com.example.som.repository.BoardMapper;
import com.example.som.repository.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final ReplyMapper replyMapper;
	private final SavedFileService savedFileService;
	
	public List<Board> findBoards(BoardCategory board_category, String searchText, int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		
		return boardMapper.findBoards(board_category, searchText, rowBounds);
	}
	
	@Transactional
	public void saveBoard(Board board, MultipartFile file) {
		boardMapper.saveBoard(board);
		
		if(file != null && file.getSize() > 0) {
			savedFileService.saveBoardFile(file, board.getBoard_id());
		}
	}
	
	public Board findBoardById(Long board_id) {
		return boardMapper.findBoardById(board_id);
	}
	
	@Transactional
	public void readBoard(Long board_id) {
		Board board = findBoardById(board_id);
		board.addHit();
		boardMapper.addHit(board);
	}
	
	@Transactional
	public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
		Board board = boardMapper.findBoardById(updateBoard.getBoard_id());
		if(board != null) {
			boardMapper.updateBoard(updateBoard);
			SavedFile savedFile = savedFileService.findBoardFile(updateBoard.getBoard_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				savedFileService.removeSavedFile(savedFile.getFile_id());
			}
			if(file != null && file.getSize() > 0) {
				savedFileService.saveBoardFile(file, board.getBoard_id());
			}
		}
	}
	
	@Transactional
	public void removeBoard(Long board_id) {
		SavedFile savedFile = savedFileService.findBoardFile(board_id);
		log.info("file:{}", savedFile);
		if(savedFile != null) {
			savedFileService.removeSavedFile(savedFile.getFile_id());
		}
		replyMapper.removeAllReply(board_id);
		boardMapper.removeBoard(board_id);
	}

	public int getTotal(BoardCategory board_category, String searchText) {
		return boardMapper.getTotal(board_category, searchText);
	}

}
