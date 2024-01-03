package com.example.som.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.etc.EtcBoard;
import com.example.som.model.file.SavedFile;
import com.example.som.repository.EtcMapper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EtcService {
	
	private final EtcMapper etcMapper;
	private final SavedFileService savedFileService;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	public List<EtcBoard> findBoards() {
		return etcMapper.findBoards();
	}
	
	public int getTotal() {
		return etcMapper.getTotal();
	}
	
	@Transactional
	public void saveBoard(EtcBoard etcBoard, MultipartFile file) {
		etcBoard.setHit(0L);
		etcMapper.saveBoard(etcBoard);	
		if(file != null && file.getSize() > 0) {
			savedFileService.saveEtcFile(file, etcBoard.getEtc_id());
		}
	}
	
	public EtcBoard findBoardById(Long etc_id) {
		return etcMapper.findBoardById(etc_id);
	}
	
	@Transactional
    public EtcBoard readEtcBoard(Long etc_id) {
        EtcBoard etcBoard = findBoardById(etc_id);
        log.info("serviceEtc:{}", etcBoard);
        etcBoard.addHit();
        etcMapper.updateBoard(etcBoard);
        
        return etcBoard;
    }
	
	@Transactional
	public void updateBoard(EtcBoard updateBoard, boolean isFileRemoved, MultipartFile file) {
		EtcBoard etcBoard = etcMapper.findBoardById(updateBoard.getEtc_id());
		if(etcBoard != null) {
			etcMapper.updateBoard(updateBoard);
			SavedFile savedFile = savedFileService.findEtcFile(updateBoard.getEtc_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				savedFileService.removeSavedFile(savedFile.getFile_id());
			}
			
			if(file != null && file.getSize() > 0) {
				savedFileService.saveEtcFile(file, updateBoard.getEtc_id());
			}
		}
	}
	
	@Transactional
	public void removeBoard(Long etc_id) {
		SavedFile savedFile = savedFileService.findEtcFile(etc_id);
		if(savedFile != null) {
			savedFileService.removeSavedFile(savedFile.getFile_id());
		}
		etcMapper.removeBoard(etc_id);
	}
	

}
