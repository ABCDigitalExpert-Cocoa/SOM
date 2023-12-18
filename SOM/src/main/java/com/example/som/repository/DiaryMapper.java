package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.som.model.diary.Diary;

@Mapper
public interface DiaryMapper {
	void saveDiary(Diary diary);
	
	List<Diary> findMyDiary(String member_id, RowBounds rowBounds);
	
	Diary findDiary(Long diary_id);

	void updateDiary(Diary updateDiary);
	
	void removeDiary(Long diary_id);

	int getTotal(String member_id);

	List<Diary> findAllOpenDiary();
}
