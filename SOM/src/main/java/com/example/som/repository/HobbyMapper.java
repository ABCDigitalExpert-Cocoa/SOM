package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.som.model.hobby.HobbyBoard;
import com.example.som.model.hobby.HobbyCategory;

@Mapper
public interface HobbyMapper {
	
	List<HobbyBoard> findBoards(RowBounds rowBounds);

	List<HobbyBoard> findBoardsByCategory(HobbyCategory hobby_category, RowBounds rowBounds);

	HobbyBoard findBoardById(Long hobby_id);
	
	void saveBoard(HobbyBoard hobbyBoard);

	void updateBoard(HobbyBoard hobbyBoard);

	void removeBoard(Long hobby_id);

	int getTotal(@RequestParam(required = false) HobbyCategory hobby_category);
}
