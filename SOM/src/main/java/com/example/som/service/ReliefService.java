package com.example.som.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.som.model.file.SavedFile;
import com.example.som.model.member.Member;
import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;
import com.example.som.repository.MemberMapper;
import com.example.som.repository.ReliefMapper;
import com.example.som.util.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReliefService {
	
	private final MemberMapper memberMapper;
	private final ReliefMapper reliefMapper;
	private final FileService fileService;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
//	public List<Relief> findReliefs(ReliefCategory relief_category, String searchText, int startRecord, int countPerPage) {
//		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
//		return reliefMapper.findReliefs(relief_category, searchText, rowBounds);
//	}
	
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
	public void updateRelief(Relief updateRelief, boolean isFileRemoved, MultipartFile file) {
		Relief relief = reliefMapper.findReliefById(updateRelief.getSeq_id());
		if(relief != null) {
			reliefMapper.updateRelief(relief);
			SavedFile savedFile = reliefMapper.findFileBySeqId(updateRelief.getSeq_id());
			if(savedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				removeSavedFile(savedFile.getFile_id());
			}
			if(file != null && file.getSize() > 0) {
				SavedFile newFile = fileService.saveFile(file);
				newFile.setSeq_id(updateRelief.getSeq_id());
				reliefMapper.saveReliefFile(newFile);
			}
		}
	}
	

	public void removeRelief(Long seq_id) {
		SavedFile savedFile = findFileBySeqId(seq_id);
		if(savedFile != null) {
			removeSavedFile(savedFile.getFile_id());
		}
		reliefMapper.removeRelief(seq_id);
	}

	@Transactional
	public void removeSavedFile(Long file_id) {
		SavedFile savedFile = reliefMapper.findFileByFileId(file_id);
		if(savedFile != null) {
			String fullPath = uploadPath + "/" + savedFile.getSaved_filename();
            fileService.deleteFile(fullPath);
            reliefMapper.removeSavedFile(savedFile.getFile_id());
		}
	}
	
	public SavedFile findFileByFileId(Long file_id) {
		return reliefMapper.findFileByFileId(file_id);
	}

	public SavedFile findFileBySeqId(Long seq_id) {
		return reliefMapper.findFileBySeqId(seq_id);
	}

	public int getTotal(ReliefCategory relief_category, String searchText) {
		return reliefMapper.getTotal(relief_category, searchText);
	}
	
//	@Transactional
//	public void increasePoint(String member_id, int addPoint) {
//	    // member_id를 이용해 Member 정보를 가져오고 Point를 증가시킴
//	    Member member = memberMapper.findMember(member_id);
//	 // 가져온 Member가 존재하면
//	    if (member != null) {
//	        // 현재 포인트를 가져와서 증가시킴
//	        int currentPoint = member.getPoint();
//	        member.setPoint(currentPoint + addPoint);
//
//	        // 업데이트된 Member 정보를 데이터베이스에 저장
//	        memberMapper.saveMember(member);
//	    }
//	}
	
	@Transactional
	public void practiceRelief(Long seq_id, String member_id) {
	    // 게시글을 가져온다.
	    Relief relief = reliefMapper.findReliefById(seq_id);

	    // 멤버를 가져온다.
	    Member member = memberMapper.findMember(member_id);

	    // 실천하기 버튼을 누른 게시글의 MBTI와 멤버의 MBTI가 같으면 포인트를 10 올린다.
	    if (relief.getMbti().equals(member.getMbti())) {
	        member.setPoint(member.getPoint() + 10);
	        memberMapper.saveMember(member);
	    }
	}
	
	public List<Relief> getReliefs(ReliefCategory relief_category, String searchText, String mbti, Long stress_level,
			int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		return reliefMapper.getReliefs(relief_category, searchText, mbti, stress_level, rowBounds);
	}

}
