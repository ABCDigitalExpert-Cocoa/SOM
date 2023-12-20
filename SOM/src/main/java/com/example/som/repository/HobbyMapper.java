package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.som.model.hobby.HobbyBoard;
import com.example.som.model.hobby.Region;

@Mapper
public interface HobbyMapper {
	
	List<HobbyBoard> findBoards(RowBounds rowBounds);

	List<HobbyBoard> findBoardsByRegion(Region region, RowBounds rowBounds);

	void saveBoard(HobbyBoard hobbyBoard);
	
	HobbyBoard findBoardById(Long hobby_id);

	void updateBoard(HobbyBoard hobbyBoard);

	void removeBoard(Long hobby_id);

	int getTotal(@RequestParam(required = false) Region region);
}
