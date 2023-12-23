package com.example.som.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.file.SavedFile;
import com.example.som.repository.FileMapper;
import com.example.som.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedFileService {
	
	private final FileMapper fileMapper;
	private final FileService fileService;
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Transactional
	public void removeSavedFile(Long file_id) {
		SavedFile savedFile = fileMapper.findFileByFileId(file_id);
		if(savedFile != null) {
			String fullPath = uploadPath + "/" + savedFile.getSaved_filename();
            fileService.deleteFile(fullPath);
            fileMapper.removeSavedFile(savedFile.getFile_id());
		}
	}
	
	public SavedFile findFileByFileId(Long file_id) {
		return fileMapper.findFileByFileId(file_id);
	}

	public SavedFile findBoardFile(Long board_id) {
		return fileMapper.findBoardFile(board_id);
	}
	
	public SavedFile findDiaryFile(Long diary_id) {
		return fileMapper.findDiaryFile(diary_id);
	}
	
	public SavedFile findReliefFile(Long relief_id) {
		return fileMapper.findReliefFile(relief_id);
	}
	
	public SavedFile findHobbyFile(Long hobby_id) {
		return fileMapper.findHobbyFile(hobby_id);
	}
	
	public SavedFile findEtcFile(Long etc_id) {
		return fileMapper.findEtcFile(etc_id);
	}
	
	public void saveBoardFile(MultipartFile file, Long board_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setBoard_id(board_id);
		fileMapper.saveBoardFile(savedFile);
	}
	
	public void saveDiaryFile(MultipartFile file, Long diary_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setDiary_id(diary_id);
		fileMapper.saveDiaryFile(savedFile);
	}
	
	public void saveReliefFile(MultipartFile file, Long relief_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setRelief_id(relief_id);
		fileMapper.saveReliefFile(savedFile);
	}
	
	public void saveHobbyFile(MultipartFile file, Long hobby_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setHobby_id(hobby_id);
		fileMapper.saveHobbyFile(savedFile);
	}
	
	public void saveEtcFile(MultipartFile file, Long etc_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setEtc_id(etc_id);
		log.info("savedFile : {}", savedFile);
		fileMapper.saveEtcFile(savedFile);
	}

	
}
