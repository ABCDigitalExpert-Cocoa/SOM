package com.example.som.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.file.SavedFile;
import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;
import com.example.som.repository.ReliefMapper;
import com.example.som.util.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReliefService {
	
	private final ReliefMapper reliefMapper;
	private final FileService fileService;
	
	public List<Relief> findReliefs(ReliefCategory relief_category) {
		
		return reliefMapper.findReliefs(relief_category);
	}
	
	@Transactional
	public void saveRelief(Relief relief, MultipartFile file) {
		reliefMapper.saveRelief(relief);
		
		if(file != null && file.getSize() > 0) {
			SavedFile savedFile = fileService.saveFile(file);
			savedFile.setSeq_id(relief.getSeq_id());
			reliefMapper.saveReliefFile(savedFile);
		}
	}
	
	public Relief findReliefById(Long seq_id) {
		return reliefMapper.findReliefById(seq_id);
	}
	
	@Transactional
	public void updateRelief(Relief relief) {
		reliefMapper.updateRelief(relief);
	}

	public void removeRelief(Long seq_id) {
		reliefMapper.removeRelief(seq_id);
	}

	public SavedFile findFileByFileId(Long file_id) {
		return reliefMapper.findFileByFileId(file_id);
	}

	public SavedFile findFileBySeqId(Long seq_id) {
		return reliefMapper.findFileBySeqId(seq_id);
	}

}
