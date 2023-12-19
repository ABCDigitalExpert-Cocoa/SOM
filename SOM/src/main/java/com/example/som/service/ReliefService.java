package com.example.som.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.config.PrincipalDetails;
import com.example.som.model.board.Board;
import com.example.som.model.file.SavedFile;
import com.example.som.model.member.Member;
import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;
import com.example.som.model.test.Test;
import com.example.som.repository.MemberMapper;
import com.example.som.repository.ReliefMapper;
import com.example.som.repository.TestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReliefService {
	
	private final ReliefMapper reliefMapper;
	private final SavedFileService savedFileService;
	private final MemberMapper memberMapper;
	private final TestMapper testMapper;
	
	
	
	public List<Relief> findReliefs(ReliefCategory relief_category, String searchText, String mbti, Long stress_level,
			int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		return reliefMapper.findReliefs(relief_category, searchText, mbti, stress_level, rowBounds);
	}
	
	@Transactional
	public void saveRelief(Relief relief, MultipartFile file) {
		reliefMapper.saveRelief(relief);
		
		if(file != null && file.getSize() > 0) {
			savedFileService.saveReliefFile(file, relief.getRelief_id());
		}
	}
	
	public Relief findReliefById(Long relief_id) {
		return reliefMapper.findReliefById(relief_id);
	}

	
	@Transactional
	public void practiceRelief(Long relief_id, Member member) {
		
		Relief relief = reliefMapper.findReliefById(relief_id);
		member.addPoint();
		memberMapper.addPoint(member);
		
    }

	
	@Transactional
	public void updateRelief(Relief updateRelief, boolean isFileRemoved, MultipartFile file) {
		Relief relief = reliefMapper.findReliefById(updateRelief.getRelief_id());
		if(relief != null) {
			reliefMapper.updateRelief(updateRelief);
			SavedFile savedFile = savedFileService.findReliefFile(updateRelief.getRelief_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				savedFileService.removeSavedFile(savedFile.getFile_id());
			}
			if(file != null && file.getSize() > 0) {
				savedFileService.saveBoardFile(file, relief.getRelief_id());
			}
		}
	}

	@Transactional
	public void removeRelief(Long relief_id) {
		SavedFile savedFile = savedFileService.findReliefFile(relief_id);
		log.info("file:{}", savedFile);
		if(savedFile != null) {
			savedFileService.removeSavedFile(savedFile.getFile_id());
		}
		reliefMapper.removeRelief(relief_id);
	}

	public int getTotal(ReliefCategory relief_category, String searchText) {
		return reliefMapper.getTotal(relief_category, searchText);
	}
	
	public Test findStressLevelbyId(String member_id) {
		return testMapper.getResentStressLevel(member_id);
	}


}
