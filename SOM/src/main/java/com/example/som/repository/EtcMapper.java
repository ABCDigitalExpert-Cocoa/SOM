package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.som.model.etc.EtcBoard;

@Mapper
public interface EtcMapper {

	List<EtcBoard> findBoards();
	
	void saveBoard(EtcBoard etcBoard);
	
	EtcBoard findBoardById(Long etc_id);
	
	void updateBoard(EtcBoard etcBoard);
	
	void removeBoard(Long etc_id);
	
	int getTotal();
}
