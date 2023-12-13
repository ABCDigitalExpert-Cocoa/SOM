package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.file.SavedFile;
import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;

@Mapper
public interface ReliefMapper {

	List<Relief> findReliefs(ReliefCategory relief_category);

	void saveRelief(Relief relief);
	
	Relief findReliefById(Long seq_id);

	void updateRelief(Relief relief);

	void removeRelief(Long seq_id);

	SavedFile findFileByFileId(Long file_id);
	
	void saveReliefFile(SavedFile savedFile);

	SavedFile findFileBySeqId(Long seq_id);

}