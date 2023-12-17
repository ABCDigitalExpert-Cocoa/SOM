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

	public SavedFile findFileBySeqId(Long seq_id) {
		return fileMapper.findFileBySeqId(seq_id);
	}
	
	public SavedFile findFileByDiaryId(Long diary_id) {
		return fileMapper.findFileByDiaryId(diary_id);
	}
	
	public void saveBoardFile(MultipartFile file, Long seq_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setSeq_id(seq_id);
		fileMapper.saveBoardFile(savedFile);
	}
	
	public void saveDiaryFile(MultipartFile file, Long diary_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setDiary_id(diary_id);
		fileMapper.saveDiaryFile(savedFile);
	}
	
	public void saveReliefFile(MultipartFile file, Long seq_id) {
		SavedFile savedFile = fileService.saveFile(file);
		savedFile.setSeq_id(seq_id);
		fileMapper.saveReliefFile(savedFile);
	}
}
