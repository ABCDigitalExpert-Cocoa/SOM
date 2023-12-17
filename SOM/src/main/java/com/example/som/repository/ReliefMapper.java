package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.example.som.model.file.SavedFile;
import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;

@Mapper
public interface ReliefMapper {

	List<Relief> findReliefs(@Param("relief_category")ReliefCategory relief_category, @Param("searchText")String searchText, @Param("mbti")String mbti, Long stress_level, RowBounds rowBounds);

	void saveRelief(Relief relief);
	
	Relief findReliefById(Long seq_id);

	void updateRelief(Relief relief);

	void removeRelief(Long seq_id);

	SavedFile findFileByFileId(Long file_id);
	
	void saveReliefFile(SavedFile savedFile);

	SavedFile findFileBySeqId(Long seq_id);

	int getTotal(@Param("relief_category")ReliefCategory relief_category, @Param("searchText")String searchText);

}
