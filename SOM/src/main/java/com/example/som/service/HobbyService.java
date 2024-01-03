package com.example.som.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.file.SavedFile;
import com.example.som.model.hobby.HobbyBoard;
import com.example.som.model.hobby.Region;
import com.example.som.repository.HobbyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HobbyService {
	private final HobbyMapper hobbyMapper;
	private final SavedFileService savedFileService;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	public List<HobbyBoard> findBoards(@RequestParam(required = false) Region region, int startRecord, int countPerPage) {
		log.info("region: {}", region);
		
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		
		List<HobbyBoard> hobbyBoards;
		
		if(region != null) {
			log.info("region: {}", region);
			hobbyBoards = hobbyMapper.findBoardsByRegion(region, rowBounds);
		} else {
			hobbyBoards = hobbyMapper.findBoards(rowBounds);
		}
		
		log.info("hobbyBoards: {}", hobbyBoards);
		
		return hobbyBoards;
	}
	
	@Transactional
	public void saveBoard(HobbyBoard hobbyBoard, MultipartFile file) {
		hobbyMapper.saveBoard(hobbyBoard);
		
		if(file != null && file.getSize() > 0) {
			savedFileService.saveHobbyFile(file, hobbyBoard.getHobby_id());
		}
	}
	
	public HobbyBoard findBoardById(Long hobby_id) {
		return hobbyMapper.findBoardById(hobby_id);
	}
	
	@Transactional
	public void updateBoard(HobbyBoard updateBoard, boolean isFileRemoved, MultipartFile file) {
		HobbyBoard hobbyBoard = hobbyMapper.findBoardById(updateBoard.getHobby_id());
		if(hobbyBoard != null) {
			hobbyMapper.updateBoard(updateBoard);
			SavedFile savedFile = savedFileService.findHobbyFile(updateBoard.getHobby_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				savedFileService.removeSavedFile(savedFile.getFile_id());
			}
			
			if(file != null && file.getSize() > 0) {
				savedFileService.saveHobbyFile(file, updateBoard.getHobby_id());
			}
		}
	}
	
	@Transactional
	public void removeBoard(Long hobby_id) {
		SavedFile savedFile = savedFileService.findHobbyFile(hobby_id);
		log.info("savedFile: {}", savedFile);
		if(savedFile != null) {
			savedFileService.removeSavedFile(savedFile.getFile_id());
		}
		hobbyMapper.removeBoard(hobby_id);
	}

	public int getTotal(@RequestParam(required = false) Region region) {
		return hobbyMapper.getTotal(region);
	}
	
	@Transactional
    public HobbyBoard readHobbyBoard(Long hobby_id) {
        HobbyBoard hobbyBoard = findBoardById(hobby_id);
        log.info("serviceHobby:{}", hobbyBoard);
        hobbyBoard.addHit();
        hobbyMapper.updateBoard(hobbyBoard);
        
        return hobbyBoard;
    }
	
}
