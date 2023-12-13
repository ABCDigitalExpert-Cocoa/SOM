package com.example.som.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	public List<Board> findBoards(BoardCategory board_category, String searchText, int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		
		return boardMapper.findBoards(board_category, searchText, rowBounds);
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
	public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
		Board board = boardMapper.findBoardById(updateBoard.getSeq_id());
		if(board != null) {
			boardMapper.updateBoard(board);
			SavedFile savedFile = boardMapper.findFileBySeqId(updateBoard.getSeq_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				removeSavedFile(savedFile.getFile_id());
			}
			if(file != null && file.getSize() > 0) {
				SavedFile newFile = fileService.saveFile(file);
				newFile.setSeq_id(updateBoard.getSeq_id());
				boardMapper.saveBoardFile(newFile);
			}
		}
	}
	
	@Transactional
	public void removeSavedFile(Long file_id) {
		SavedFile savedFile = boardMapper.findFileByFileId(file_id);
		if(savedFile != null) {
			String fullPath = uploadPath + "/" + savedFile.getSaved_filename();
            fileService.deleteFile(fullPath);
            boardMapper.removeSavedFile(savedFile.getFile_id());
		}
	}

	public void removeBoard(Long seq_id) {
		SavedFile savedFile = findFileBySeqId(seq_id);
		if(savedFile != null) {
			removeSavedFile(savedFile.getFile_id());
		}
		boardMapper.removeBoard(seq_id);
	}

	public SavedFile findFileByFileId(Long file_id) {
		return boardMapper.findFileByFileId(file_id);
	}

	public SavedFile findFileBySeqId(Long seq_id) {
		return boardMapper.findFileBySeqId(seq_id);
	}
	
	public int getTotal(BoardCategory board_category, String searchText) {
		return boardMapper.getTotal(board_category, searchText);
	}

}
