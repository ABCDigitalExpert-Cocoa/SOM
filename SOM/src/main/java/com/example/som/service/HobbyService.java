package com.example.som.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.file.SavedFile;
import com.example.som.model.hobby.HobbyBoard;
import com.example.som.model.hobby.HobbyCategory;
import com.example.som.repository.BoardMapper;
import com.example.som.repository.HobbyMapper;
import com.example.som.util.FileService;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HobbyService {
	private final HobbyMapper hobbyMapper;
	private final FileService fileService;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	public List<HobbyBoard> findBoards(HobbyCategory hobby_category, int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		
		return hobbyMapper.findBoards(hobby_category, rowBounds);
	}
	
	@Transactional
	public void saveBoard(HobbyBoard hobbyBoard, MultipartFile file) {
		
	    // 현재 날짜와 시간을 가져와서 create_date에 설정
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    hobbyBoard.setCreate_date(currentDateTime);
	    hobbyBoard.setUpdate_date(currentDateTime);
		hobbyMapper.saveBoard(hobbyBoard);
		
		if(file != null && file.getSize() > 0) {
			SavedFile savedFile = fileService.saveFile(file);
			savedFile.setSeq_id(hobbyBoard.getSeq_id());
			hobbyMapper.saveBoardFile(savedFile);
		}
	}
	
	public HobbyBoard findBoardById(Long seq_id) {
		return hobbyMapper.findBoardById(seq_id);
	}
	
	@Transactional
	public void updateBoard(HobbyBoard updateBoard, boolean isFileRemoved, MultipartFile file) {
		HobbyBoard hobbyBoard = hobbyMapper.findBoardById(updateBoard.getSeq_id());
		if(hobbyBoard != null) {
			hobbyMapper.updateBoard(updateBoard);
			SavedFile savedFile = hobbyMapper.findFileBySeqId(updateBoard.getSeq_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				removeSavedFile(savedFile.getFile_id());
			}
			
			if(file != null && file.getSize() > 0) {
				SavedFile newFile = fileService.saveFile(file);
				newFile.setSeq_id(updateBoard.getSeq_id());
				hobbyMapper.saveBoardFile(newFile);
			}
		}
	}
	
	@Transactional
	public void removeSavedFile(Long file_id) {
		SavedFile savedFile = hobbyMapper.findFileByFileId(file_id);
		if(savedFile != null) {
			String fullPath = uploadPath + "/" + savedFile.getSaved_filename();
            fileService.deleteFile(fullPath);
            hobbyMapper.removeSavedFile(savedFile.getFile_id());
		}
	}

	public void removeBoard(Long seq_id) {
		SavedFile savedFile = findFileBySeqId(seq_id);
		if(savedFile != null) {
			removeSavedFile(savedFile.getFile_id());
		}
		hobbyMapper.removeBoard(seq_id);
	}

	public SavedFile findFileByFileId(Long file_id) {
		return hobbyMapper.findFileByFileId(file_id);
	}

	public SavedFile findFileBySeqId(Long seq_id) {
		return hobbyMapper.findFileBySeqId(seq_id);
	}
	
	public int getTotal(HobbyCategory hobby_category) {
		return hobbyMapper.getTotal(hobby_category);
	}
	
    public HobbyBoard readHobbyBoard(Long seq_id) {
        HobbyBoard hobbyBoard = findBoardById(seq_id);
        hobbyBoard.addHit();
        updateBoard(hobbyBoard, false, null);
        return hobbyBoard;
    }
	
}
