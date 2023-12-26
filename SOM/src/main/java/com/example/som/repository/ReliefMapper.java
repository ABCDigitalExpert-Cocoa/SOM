package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.example.som.model.relief.Relief;
import com.example.som.model.relief.ReliefCategory;

@Mapper
public interface ReliefMapper {

	List<Relief> findReliefs(@Param("relief_category")ReliefCategory relief_category, @Param("searchText")String searchText, @Param("mbti")String mbti, @Param("stress_level")Long stress_level, RowBounds rowBounds);

	Relief findReliefById(Long relief_id);
	
	void saveRelief(Relief relief);

	void updateRelief(Relief relief);

	void removeRelief(Long relief_id);

	int getTotal(@Param("relief_category")ReliefCategory relief_category, @Param("searchText")String searchText, @Param("mbti") String mbti, @Param("stress_level") Long stress_level);


}
